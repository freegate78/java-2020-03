package com.akproductions;

import com.akproductions.proxycalldemo.Ioc;
import com.akproductions.testlogging.TestLogging;
import com.akproductions.testlogging.TestLoggingInterface;

public class Main {
    public static void main(String[] args)
            {
                TestLogging object = new TestLogging();
                TestLoggingInterface myClass = Ioc.createMyClass();
                myClass.calculation(10);
    }

}
 