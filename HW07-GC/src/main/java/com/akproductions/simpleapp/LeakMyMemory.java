package com.akproductions.simpleapp;

import java.util.ArrayList;

public class LeakMyMemory{
    ArrayList<String>[] arrObject;

    int sizeOfArray;
    int elementOfArray;

    public LeakMyMemory(int sizeOfArray) {
        arrObject=new ArrayList[sizeOfArray];
        this.sizeOfArray=sizeOfArray;
        this.elementOfArray=0;
    }

    public void addMemoryLeaker() {
        if(elementOfArray>=sizeOfArray){elementOfArray=0;}
        arrObject[elementOfArray++]=new ArrayList<>(sizeOfArray);
    }
}
