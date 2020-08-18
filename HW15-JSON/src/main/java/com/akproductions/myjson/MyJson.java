package com.akproductions.myjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.json.*;


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
        AnyObject obj = new AnyObject().init();
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
        getInt(int.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), field.getInt(object));
            }
        }, getByte(byte.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), field.getByte(object));
            }
        }, getChar(char.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), String.valueOf(field.getChar(object)));
            }
        }, getLong(long.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), field.getLong(object));
            }
        }, getWInt(Integer.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), (Integer) field.get(object));
            }
        }, getWByte(Byte.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), (Byte) field.get(object));
            }
        }, getWChar(Character.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), String.valueOf((Character) field.get(object)));
            }
        }, getWLong(Long.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                return Json.createObjectBuilder().add(field.getName(), (Long) field.get(object));
            }
        }, getWARRINT(int[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add((int) Array.get(fieldValue, i));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getWARRBYTE(byte[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add((byte) Array.get(fieldValue, i));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getWARRCHAR(char[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add(String.valueOf(Array.get(fieldValue, i)));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getWARRLONG(long[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add((long) Array.get(fieldValue, i));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getW_ARRINT(Integer[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add((Integer) Array.get(fieldValue, i));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getW_ARRBYTE(Byte[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add((Byte) Array.get(fieldValue, i));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getW_ARRCHAR(Character[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add(String.valueOf(Array.get(fieldValue, i)));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getW_ARRLONG(Long[].class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    jsonObjectInner.add((Long) Array.get(fieldValue, i));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        }, getWCOLLECTION(java.util.Collection.class) {
            @Override
            public JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException {
                var jsonObjectInner = Json.createArrayBuilder();
                var fieldValue = field.get(object);
                for (int i = 0; i < Collection.class.cast(fieldValue).size(); i++) {
                    jsonObjectInner.add(String.valueOf(Collection.class.cast(fieldValue).toArray()[i]));
                }
                return Json.createObjectBuilder().add(field.getName(), jsonObjectInner);
            }
        };
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

        public abstract JsonObjectBuilder addToJson(Field field, Object object) throws IllegalAccessException;
    }

    private static String toMyJson(Object object, Class objClass) throws Exception {
        Field[] fieldsAll = objClass.getDeclaredFields();

        var jsonObject = Json.createObjectBuilder();

        for (Field field : fieldsAll) {
            field.setAccessible(true);

            if (FieldsToRead.find(field.getType()).isEmpty()) {
                System.out.println("type of field " + field.getName() + " " + field.getType() + " is unsupported, skipping!!!");
            } else {
                System.out.println("field " + field.getName() + " is type of " + FieldsToRead.find(field.getType()).get().getFieldsToRead());

                if (field.get(object) == null) {
                    System.out.println("not need to serialize null like gson...");
                } else {
                    System.out.println("convert to json with factory handler " + FieldsToRead.find(field.getType()).get().name());
                    jsonObject.addAll(FieldsToRead.find(field.getType()).get().addToJson(field, object));
                }
            }
        }
        return jsonObject.build().toString();
    }
}
