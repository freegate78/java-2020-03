package com.akproductions.testlogging;

import com.akproductions.testlogging.annotations.Log;

public class TestLogging implements TestLoggingInterface{
    @Override
    public void calculation(int param) {
        System.out.println("called method calculation with param "+param);
    };
}