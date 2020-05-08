package com.akproductions;

import com.akproductions.testframework.TestFramework;

public class Hw06_main {

    public static void main(String[] args) throws Exception {
        String ClassToTest = "com.akproductions.tests.MessageBuilderImplTest";
        System.out.println((new TestFramework(ClassToTest)).doTest());
    }
}
