package com.akproductions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class hw06_main {
    public static final String TEMPLATE_NAME = "AnyTemplate";
    public static final String TEMPLATE_TEXT = "Hi!\n %s \n With best regards, %s";
    public static final String MESSAGE_TEXT = "How you doing?";
    public static final String SIGN = "Vasya";
    private MessageTemplateProvider provider;
    private MessageBuilder messageBuilder;

    public static void main(String[] args) throws Exception {
        //входной параметр - имя класса
        String ClassToTest = "com.akproductions.MessageBuilderImpl";
        Class clazz = Class.forName(ClassToTest);
        //Class<MessageBuilderImpl> clazz = MessageBuilderImpl.class;
        System.out.println("[INFO] Class Name:" + clazz.getSimpleName());

        //System.out.println("--- annotations:");
        //методы читсто в нашем классе - чтобы следить за аннотациями
        Method[] methodsAll = clazz.getDeclaredMethods();
        Method[] methodsBefore = new Method[100] ;
        Method[] methodsAfter = new Method[100] ;
        Method[] methodsTest = new Method[100] ;
        int cBefore =0;int cAfter =0;int cTest =0;
        int testSuccess=0;int testError=0;
        for (Method method : methodsAll) {
//            System.out.println(method.getName());
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                switch (annotation.toString()){
                    case  ("@com.akproductions.Before()"):
                        methodsBefore[cBefore++]=method;
                        break;
                    case  ("@com.akproductions.After()"):
                        methodsAfter[cAfter++]=method;
                        break;
                    case  ("@com.akproductions.Test()"):
                        methodsTest[cTest++]=method;
                        break;
                    default:
                        System.out.println("[WARNING] unknown annotation: " + annotation);
                        break;
                }
//                System.out.println(annotation);
            }
        }
            //);
        System.out.println("[INFO] --- Before methods:");
        for (int i =0; i<cBefore; i++) {System.out.println(methodsBefore[i].getName());}
        System.out.println("[INFO] --- After methods:");
        for (int i =0; i<cAfter; i++) {System.out.println(methodsAfter[i].getName());}
        System.out.println("[INFO] --- Test methods:");
        for (int i =0; i<cTest; i++) {System.out.println(methodsTest[i].getName());}

        //make tests
                for (int i =0; i<cTest; i++) {
                    System.out.println("[INFO] --- run test for  " + methodsTest[i].getName());

                    try {
                        //Constructor<?>[] constructors = clazz.getConstructors();
                        //System.out.println("--- constructors:");
                        //System.out.println(Arrays.toString(constructors));

                        //System.out.println("--- creating new object:");
                        MessageTemplateProvider provider = new MessageTemplateProvider();
                        Constructor<MessageBuilderImpl> constructor = clazz.getConstructor(MessageTemplateProvider.class);
                        MessageBuilderImpl object = constructor.newInstance(provider);
                        for (int j = 0; j < cBefore; j++) {
                            System.out.println("[DEBUG] --- invoke before method  " + methodsBefore[j].getName());
                            methodsBefore[j].setAccessible(true);
                            methodsBefore[j].invoke(object);
                        }

                        System.out.println("[DEBUG] --- invoke test method  " + methodsTest[i].getName());
                        methodsTest[i].setAccessible(true);

                        //2 вида запуска - с аргументом и без проверяем
                        var result = new Object();
                        try {
                            result = methodsTest[i].invoke(object);
                            System.out.println("[DEBUG] --- invoked test method  " + methodsTest[i].getName() + " without arguments");
                        } catch (IllegalArgumentException e1) {
                                result = methodsTest[i].invoke(object, 0);
                                System.out.println("[DEBUG] --- invoked test method  " + methodsTest[i].getName() + " with arguments");
                            }
                        catch (InvocationTargetException e1) {
                            System.out.println("[DEBUG] --- invoked test method  " + methodsTest[i].getName() + " without arguments has target exception, reason: " + e1.getTargetException());
                            throw e1;
                        }
                        catch (Exception e1) {
                            System.out.println("[DEBUG] --- invoked test method  " + methodsTest[i].getName() + " without arguments failed, reason: " + e1.getLocalizedMessage());
                            throw e1;
                        }
                        System.out.println("[DEBUG] --- test method result is: " + result);

                        for (int k = 0; k < cAfter; k++) {
                            System.out.println("[DEBUG] --- invoke after method  " + methodsAfter[k].getName());
                            methodsAfter[k].setAccessible(true);
                            methodsAfter[k].invoke(object);
                        }
                        testSuccess++;
                        System.out.println("[INFO] --- test runned successfully:" + object.getValue());

                    } catch (Exception e) {
                        testError++;
                        System.out.println("[ERROR] --- testing of test method " + methodsTest[i].getName() + " failed, reason stack is: ");

                        StackTraceElement[] stackTraceElements = e.getStackTrace();
                        for(var info: stackTraceElements)
                            System.out.println(info);
                    }

                }

                System.out.println("[INFO] --- total tests runned: " + (testSuccess+testError) + ", successfully runned: " + testSuccess + ", not runned because of an error: " + testError);


    }
}
