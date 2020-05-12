package com.akproductions.testframework;

public class TestResults  {
    private int successCount;
    private int failedCount;
    private int totalCount;

    public TestResults() {
        this.successCount = 0;
        this.failedCount = 0;
        this.totalCount = 0;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void addSuccessCount() {
        this.successCount++;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public void addFailedCount() {
        this.failedCount++;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void printResults()
    {

        System.out.println("total tests:"+this.totalCount+ ", success tests:"+this.successCount+", failed tests:"+this.failedCount);
    }
}

