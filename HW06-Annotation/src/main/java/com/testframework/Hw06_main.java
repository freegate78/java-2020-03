package com.testframework;

public class Hw06_main {

    public static void main(String[] args) throws Exception {
        String ClassToTest = "com.akproductions.MessageBuilderImpl";
        System.out.println((new TestFramework(ClassToTest)).doTest());
    }
}
