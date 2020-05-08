package com.akproductions.testframework;

import com.akproductions.testframework.annotations.After;
import com.akproductions.testframework.annotations.Before;
import com.akproductions.testframework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFramework {
    private Class clazz;
    private HashMap<String,Integer> testResults;
    public TestFramework (String classToTest) throws Exception {
        clazz = Class.forName(classToTest);
        testResults= new HashMap<String,Integer> ();
    }

    public HashMap<String,Integer> doTest () throws Exception
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
        methodsTest.forEach(methodTest ->{
            try {
                var object = clazz.getConstructor().newInstance();
                invokeMethods(methodsBefore,object,"Before");
                methodTest.setAccessible(true);
                try {
                    methodTest.invoke(object);
                    testSuccess.getAndIncrement();
                    System.out.println("[INFO] --- test "+methodTest.getName()+" completed successfully");
                } catch (Exception e) {
                    testError.getAndIncrement();
                    System.out.println("[ERROR] --- error in calling test method  " + methodTest.getName()+ ". reason: " + e.getLocalizedMessage());
                } finally {invokeMethods(methodsAfter,object,"After");}
            } catch (Exception e) {
                testError.getAndIncrement();
                System.out.println("[FATAL ERROR] --- running all test scenario for method " + methodTest.getName() + " failed, reason is: " + e.getLocalizedMessage());
            }
        });
        testResults.put("total", (testSuccess.get() + testError.get()));
        testResults.put("success", testSuccess.get());
        testResults.put("failed", testError.get());

        return testResults;
    }

    private void invokeMethods(ArrayList<Method> methods,Object object, String typeOfMethod)
    {
        methods.forEach(method ->
        {
            method.setAccessible(true);
            try {
                method.invoke(object);
            } catch (Exception e) {
                System.out.println("[ERROR] --- error in calling "+typeOfMethod+" method  " + method.getName() + ". reason: "+e.getLocalizedMessage());
            }
        });
    }

}
