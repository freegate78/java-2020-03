package com.akproductions.simpleapp;

public class SimpleApplication {
public static void main(String[] args) throws Exception
{
    printHeapStatistics("init: ");
    int sizeOfIteration=60000;
    int sizeOfArray=1000;
    var memoryLeaker=new LeakMyMemory(sizeOfArray);
    for (int i=0;i<sizeOfIteration;i++){
        memoryLeaker.addMemoryLeaker();
        //Thread.sleep(1);
        if (i%10000==0) {printHeapStatistics("iteration "+i+": ");}
    }
}
private static void printHeapStatistics(String comment){
    long currentHeapSize = Runtime.getRuntime().totalMemory()/1024768;
    long freeHeapSize = Runtime.getRuntime().freeMemory()/1024768;
    long maxHeapSize = Runtime.getRuntime().maxMemory()/1024768;
    System.out.println("["+comment+"]"+"[debug][app] MAIN(1) log log (log) (log) heapSize is " + currentHeapSize+", free memory is "+freeHeapSize+ ", max_size is "+maxHeapSize);
}
}
