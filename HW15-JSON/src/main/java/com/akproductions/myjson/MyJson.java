package com.akproductions.myjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class MyJson {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        AnyObject obj = new AnyObject();
        System.out.println(obj);
        String myJson = toMyJson(obj, AnyObject.class);
        System.out.println(myJson);
        String json = gson.toJson(obj);
        System.out.println(json);
        AnyObject obj2 = gson.fromJson(json, AnyObject.class);
        AnyObject obj3 = gson.fromJson(myJson, AnyObject.class);
        System.out.println("compare serialize by Gson, deserialize by Gson is " + obj.equals(obj2));
        System.out.println(obj2);
        System.out.println("compare serialize by MyGson, deserialize by Gson is " + obj.equals(obj3));
        System.out.println(obj3);
    }

    public enum FieldsToRead {
        getInt(int.class), getByte(byte.class), getChar(char.class), getLong(long.class), getWInt(Integer.class), getWByte(Byte.class), getWChar(Character.class), getWLong(Long.class), getWARRINT(int[].class), getWARRBYTE(byte[].class), getWARRCHAR(char[].class), getWARRLONG(long[].class), getW_ARRINT(Integer[].class), getW_ARRBYTE(Byte[].class), getW_ARRCHAR(Character[].class), getW_ARRLONG(Long[].class), getWCOLLECTION(java.util.Collection.class);
        final Class fieldsToRead;

        FieldsToRead(Class fieldsToRead) {
            this.fieldsToRead = fieldsToRead;
        }

        public Class getFieldsToRead() {
            return fieldsToRead;
        }

        public static Optional<FieldsToRead> find(Class fieldsToRead) {
            FieldsToRead[] values = values();
            for (FieldsToRead value : values) {
                if (value.fieldsToRead == fieldsToRead)
                    return Optional.of(value);
            }
            return Optional.empty();
        }
    }

    private static String toMyJson(Object object, Class objClass) throws Exception {
        Field[] fieldsAll = objClass.getDeclaredFields();

        var jsonObject = Json.createObjectBuilder();

        for (Field field : fieldsAll) {
            field.setAccessible(true);

            System.out.println("field " + field.getName() + " is ");

            FieldsToRead fieldClass;
            Class fieldClassType;
            try {
                fieldClass = FieldsToRead.find(field.getType()).get();
                fieldClassType = fieldClass.getFieldsToRead();
                Method method = Field.class.getMethod(FieldsToRead.find(field.getType()).get().name().replaceAll("(.+)W.+$", "$1"), new Class[]{java.lang.Object.class});
                var fieldValue = method.invoke(field, object);
                System.out.println("OBJECT type discovered by ENUM is " + fieldClass.name() + "(" + fieldClass.getFieldsToRead() + "), value is " + fieldValue);

                if (fieldValue == null) {
                    System.out.println("not need to serialize null like gson...");
                } else if (int.class.equals(field.getType())) {
                    jsonObject.add(field.getName(), (int) fieldValue);
                    System.out.println("serialize primitive int ");
                } else if (long.class.equals(field.getType())) {
                    jsonObject.add(field.getName(), (long) fieldValue);
                    System.out.println("serialize primitive long ");
                } else if (byte.class.equals(field.getType())) {
                    jsonObject.add(field.getName(), (byte) fieldValue);
                    System.out.println("serialize primitive byte ");
                } else if (Integer.class.equals(field.getType())) {
                    jsonObject.add(field.getName(), (Integer) fieldValue);
                    System.out.println("serialize  " + fieldClassType);
                } else if (Long.class.equals(field.getType())) {
                    jsonObject.add(field.getName(), (Long) fieldValue);
                    System.out.println("serialize  " + fieldClassType);
                } else if (Byte.class.equals(field.getType())) {
                    jsonObject.add(field.getName(), (Byte) fieldValue);
                    System.out.println("serialize  " + fieldClassType);
                } else if (fieldClass.name().contains("ARR")) {
                    System.out.println("serialize  ARR " + fieldClassType);
                    var jsonObjectInner = Json.createArrayBuilder();
                    System.out.println("array size  is " + Array.getLength(fieldValue));
                    for (int i = 0; i < Array.getLength(fieldValue); i++) {
                        System.out.println("array element[" + i + "] is " + Array.get(fieldValue, i));
                        if (int[].class.equals(field.getType()) || Integer[].class.equals(field.getType())) {
                            jsonObjectInner.add((int) Array.get(fieldValue, i));
                        } else if (byte[].class.equals(field.getType()) || Byte[].class.equals(field.getType())) {
                            jsonObjectInner.add((byte) Array.get(fieldValue, i));
                        } else if (char[].class.equals(field.getType()) || Character[].class.equals(field.getType())) {
                            jsonObjectInner.add(String.valueOf(Array.get(fieldValue, i)));
                        } else if (long[].class.equals(field.getType()) || Long[].class.equals(field.getType())) {
                            jsonObjectInner.add((long) Array.get(fieldValue, i));
                        }

                    }
                    jsonObject.add(field.getName(), jsonObjectInner);

                } else if (fieldClass.name().contains("COLLECTION")) {
                    System.out.println("serialize  Collection " + fieldClassType);
                    var jsonObjectInner = Json.createArrayBuilder();
                    System.out.println("collection size  is " + Collection.class.cast(fieldValue).size());
                    for (int i = 0; i < Collection.class.cast(fieldValue).size(); i++) {
                        System.out.println("collection element[" + i + "] is " + Collection.class.cast(fieldValue).toArray()[i]);
                        jsonObjectInner.add(String.valueOf(Collection.class.cast(fieldValue).toArray()[i]));
                    }
                    jsonObject.add(field.getName(), jsonObjectInner);
                } else {
                    jsonObject.add(field.getName(), String.valueOf(fieldValue));
                    System.out.println("serialize by default as string - type is  " + fieldClassType);
                }

            } catch (Exception e) {
                System.out.println("Serialize action - Unrecognized type  " + field.getType() + " !!!" + e.getLocalizedMessage() + e.getStackTrace());
            }
            ;

        }
        return jsonObject.build().toString();
    }

}
