package com.akproductions.testlogging;

import com.akproductions.testlogging.annotations.Log;

public class TestLogging implements TestLoggingInterface{
    @Log
    @Override
    public void calculation(int param) {};
}