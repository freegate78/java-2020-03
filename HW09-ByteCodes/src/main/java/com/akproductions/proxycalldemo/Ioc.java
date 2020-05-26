package com.akproductions.proxycalldemo;

import com.akproductions.testlogging.TestLogging;
import com.akproductions.testlogging.TestLoggingInterface;
import com.akproductions.testlogging.annotations.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ioc {

    private Ioc() {
    }

    public static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(com.akproductions.testlogging.TestLogging.class.getClassLoader(),
                com.akproductions.testlogging.TestLogging.class.getInterfaces(), handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final  TestLoggingInterface myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Log.class))
                {
                    System.out.println("[LOG] invoking method:" + method + " with args: "+ args[0]);

                }
            }
//                myClass.getAnnotation(Log.class);
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
