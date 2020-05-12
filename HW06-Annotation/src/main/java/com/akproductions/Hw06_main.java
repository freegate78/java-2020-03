package com.akproductions;

import com.akproductions.testframework.TestFramework;
import com.akproductions.testframework.TestResults;

public class Hw06_main {

    public static void main(String[] args) throws Exception {
        String ClassToTest = "com.akproductions.tests.MessageBuilderImplTest";
        var testResults=(TestResults)new TestFramework(ClassToTest).doTest();
        testResults.printResults();
    }
}
