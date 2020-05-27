package com.akproductions.proxycalldemo;

import com.akproductions.testlogging.TestLogging;
import com.akproductions.testlogging.TestLoggingInterface;
import com.akproductions.testlogging.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ioc {

    private Ioc() {
    }

    public static TestLoggingInterface createMyClass() {
        return (TestLoggingInterface) Proxy.newProxyInstance(com.akproductions.testlogging.TestLogging.class.getClassLoader(),
                com.akproductions.testlogging.TestLogging.class.getInterfaces(),
                new DemoInvocationHandler(new TestLogging()));
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final  TestLoggingInterface myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if(method.isAnnotationPresent(Log.class))
                {
                    System.out.println("[LOG] invoking method:" + method + " with args: "+ args[0]);
                }
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
