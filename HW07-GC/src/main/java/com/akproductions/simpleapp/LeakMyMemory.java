package com.akproductions.simpleapp;

import java.util.*;
import java.util.stream.Collectors;

public class LeakMyMemory{
    ArrayList<String>[] arrObject;
    List<String>[] filteredObject;
    private static Map<String, String> mapContainer = new HashMap<String, String>();

    int sizeOfArray;
    int elementOfArray;

    public LeakMyMemory(int sizeOfArray) {
        arrObject=new ArrayList[sizeOfArray];
        filteredObject=new ArrayList[sizeOfArray];
        this.sizeOfArray=sizeOfArray;
        this.elementOfArray=0;
    }

    public void addMemoryLeaker() {
        if(elementOfArray>=sizeOfArray){elementOfArray=0;}
        arrObject[elementOfArray++]=new ArrayList<>(sizeOfArray);
        generateCpuLoad();
    }
    private void generateCpuLoad(){
        for(int x=0;x<sizeOfArray ;x++){
            String newStringData = "test" + x;
            mapContainer.put(newStringData, newStringData);
        }
        for(int x=0;x<sizeOfArray*0.5 ;x++){
            String newStringData = "test" + x;
            mapContainer.remove(newStringData);
        }
//        for(int x=0;x<sizeOfArray*0.05 ;x++){
            arrObject[0]=(ArrayList) Arrays.stream(arrObject).filter(line -> !"test".equals(line)).collect(Collectors.toList());
  //      }
    }
}
