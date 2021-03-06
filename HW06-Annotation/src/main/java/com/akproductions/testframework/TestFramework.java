package com.akproductions.testframework;

import com.akproductions.testframework.annotations.After;
import com.akproductions.testframework.annotations.Before;
import com.akproductions.testframework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework implements TestResult{
    private Class clazz;
    private TestResults testResults;
    public TestFramework (String classToTest) throws Exception {
        clazz = Class.forName(classToTest);
//        testResults= new HashMap<String,Integer> ();
        testResults= new TestResults ();
    }

    public TestResults doTest () throws Exception
    {
        Method[] methodsAll = clazz.getDeclaredMethods();
        var methodsBefore = new ArrayList<Method>(10);
        var methodsAfter = new ArrayList<Method>(10);
        var methodsTest = new ArrayList<Method>(10);
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

        return makeTests(methodsTest, methodsBefore, methodsAfter);
    }

    private TestResults makeTests(List<Method> methodsTest,List<Method> methodsBefore, List<Method> methodsAfter) throws Exception
    {
        testResults.setTotalCount(methodsTest.size());
        methodsTest.forEach(methodTest ->{
            try {
                var object = clazz.getConstructor().newInstance();
                invokeMethods(methodsBefore,object,"Before");
                methodTest.setAccessible(true);
                try {
                    methodTest.invoke(object);
                    System.out.println("[INFO] --- test "+methodTest.getName()+" completed successfully");
                    testResults.addSuccessCount();
                } catch (Exception e) {
                    System.out.println("[ERROR] --- error in calling test method  " + methodTest.getName()+ ". reason: " + e.getLocalizedMessage());
                    testResults.addFailedCount();
                } finally {invokeMethods(methodsAfter,object,"After");}
            } catch (Exception e) {
                System.out.println("[FATAL ERROR] --- running all test scenario for method " + methodTest.getName() + " failed, reason is: " + e.getLocalizedMessage());
                testResults.addFailedCount();
            }
        });
    return testResults;
    }

    private void invokeMethods(List<Method> methods, Object object, String typeOfMethod)
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
