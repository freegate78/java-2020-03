package com.akproductions.proxycalldemo;

import com.akproductions.testlogging.TestLogging;
import com.akproductions.testlogging.TestLoggingInterface;
import com.akproductions.testlogging.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class Ioc {
    static ArrayList<Class> LoggedMethods;
    private Ioc() {
    }

    private static List<Class> getLoggedMethods(Method[] methods, Class classOfAnnotation){
        ArrayList<Class> varLoggedMethods = new ArrayList(10);
        for (Method method : methods) {
            if (method.isAnnotationPresent(classOfAnnotation)){
                varLoggedMethods.add(method.getClass());
            }
        }
        return varLoggedMethods;
    }

    public static TestLoggingInterface createMyClass() {
        LoggedMethods= (ArrayList<Class>) getLoggedMethods(TestLoggingInterface.class.getDeclaredMethods(),Log.class);
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
            if(LoggedMethods.contains(method.getClass())) {System.out.println("[LOG] invoking method:" + method + " with args: "+ args[0]);}
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
