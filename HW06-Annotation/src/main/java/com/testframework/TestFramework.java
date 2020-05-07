package com.testframework;

import com.akproductions.After;
import com.akproductions.Before;
import com.akproductions.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFramework {
    Class clazz;
    public TestFramework (String classToTest) throws Exception {
        clazz = Class.forName(classToTest);
    }

    public String doTest () throws Exception
    {
        Method[] methodsAll = clazz.getDeclaredMethods();
        var methodsBefore = new ArrayList<Method>(10);
        var methodsAfter = new ArrayList<Method>(10);
        var methodsTest = new ArrayList<Method>(10);
        AtomicInteger testSuccess= new AtomicInteger();
        AtomicInteger testError= new AtomicInteger();
        for (Method method : methodsAll) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Before.class)) methodsBefore.add(method);
                if (annotation.annotationType().equals(After.class)) methodsAfter.add(method);
                if (annotation.annotationType().equals(Test.class)) methodsTest.add(method);
            }
            }
        System.out.println("[INFO] --- Before methods:");
        methodsBefore.forEach(c ->   System.out.println(c.getName()));
        System.out.println("[INFO] --- After methods:");
        methodsAfter.forEach(c ->   System.out.println(c.getName()));
        System.out.println("[INFO] --- Test methods:");
        methodsTest.forEach(c ->   System.out.println(c.getName()));

        //make tests
        methodsTest.forEach(methodTest ->
        {
            System.out.println("[INFO] --- run test for  " + methodTest.getName());
            try {
                Constructor constructor = clazz.getConstructor();
                var object = constructor.newInstance();
                    methodsBefore.forEach(methodBefore ->
                    {
                        System.out.println("[DEBUG] --- invoke before method  " + methodBefore.getName());
                        methodBefore.setAccessible(true);
                        try {
                            methodBefore.invoke(object);
                        } catch (Exception e) {
                            System.out.println("[ERROR] --- error in calling before method  " + methodBefore.getName());
                            e.printStackTrace(System.out);
                        }
                    });

                System.out.println("[DEBUG] --- invoke test method  " + methodTest.getName());
                methodTest.setAccessible(true);
                var result = new Object();
                try {
                    result=methodTest.invoke(object);
                    testSuccess.getAndIncrement();
                    System.out.println("[INFO] --- test runned successfully");
                } catch (Exception e) {
                    testError.getAndIncrement();
                    System.out.println("[ERROR] --- error in calling test method  " + methodTest.getName());
                    e.printStackTrace(System.out);

                } finally {
                    methodsAfter.forEach(methodAfter ->
                    {
                        System.out.println("[DEBUG] --- invoke before method  " + methodAfter.getName());
                        methodAfter.setAccessible(true);
                        try {
                            methodAfter.invoke(object);
                        } catch (Exception e) {
                            System.out.println("[ERROR] --- error in calling after method  " + methodAfter.getName());
                            e.printStackTrace(System.out);
                        }
                    });
                }

            } catch (Exception e) {
                testError.getAndIncrement();
                System.out.println("[FATAL ERROR] --- running all test scenario for method " + methodTest.getName() + " failed, reason stack is: ");
                e.printStackTrace(System.out);
            }

        });

        return ("[INFO] --- total tests runned: " + (testSuccess.get() + testError.get()) + ", successfully runned: " + testSuccess + ", not runned because of an error: " + testError);
    }

}
