package com.akproductions.testlogging;

import com.akproductions.testlogging.annotations.Log;

public interface TestLoggingInterface {
    @Log
    public void calculation(int param);
}
