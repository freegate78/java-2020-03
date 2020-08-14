package com.akproductions.myjson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class AnyObject {
    private int primitiveOfInt;
    private byte primitiveOfByte;
    private long primitiveOfLong;
    private char primitiveOfChar;

    private Integer wrapperOfInt;
    private Byte wrapperOfByte;
    private Long wrapperOfLong;
    private Character wrapperOfChar;

    private int [] arr_primitiveOfInt;
    private byte [] arr_primitiveOfByte;
    private long [] arr_primitiveOfLong;
    private char [] arr_primitiveOfChar;

    private Integer [] arr_wrapperOfInt;
    private Byte [] arr_wrapperOfByte;
    private Long [] arr_wrapperOfLong;
    private Character [] arr_wrapperOfChar;

    private Collection objectOfCollection;

    public AnyObject(int primitiveOfInt, byte primitiveOfByte, long primitiveOfLong, char primitiveOfChar, Integer wrapperOfInt, Byte wrapperOfByte, Long wrapperOfLong, Character wrapperOfChar, int[] arr_primitiveOfInt, byte[] arr_primitiveOfByte, long[] arr_primitiveOfLong, char[] arr_primitiveOfChar, Integer[] arr_wrapperOfInt, Byte[] arr_wrapperOfByte, Long[] arr_wrapperOfLong, Character[] arr_wrapperOfChar, Collection objectOfCollection) {
        this.primitiveOfInt = primitiveOfInt;
        this.primitiveOfByte = primitiveOfByte;
        this.primitiveOfLong = primitiveOfLong;
        this.primitiveOfChar = primitiveOfChar;
        this.wrapperOfInt = wrapperOfInt;
        this.wrapperOfByte = wrapperOfByte;
        this.wrapperOfLong = wrapperOfLong;
        this.wrapperOfChar = wrapperOfChar;
        this.arr_primitiveOfInt = arr_primitiveOfInt;
        this.arr_primitiveOfByte = arr_primitiveOfByte;
        this.arr_primitiveOfLong = arr_primitiveOfLong;
        this.arr_primitiveOfChar = arr_primitiveOfChar;
        this.arr_wrapperOfInt = arr_wrapperOfInt;
        this.arr_wrapperOfByte = arr_wrapperOfByte;
        this.arr_wrapperOfLong = arr_wrapperOfLong;
        this.arr_wrapperOfChar = arr_wrapperOfChar;
        this.objectOfCollection = objectOfCollection;
    }


    public AnyObject() {
        this.primitiveOfInt = 0;
        this.primitiveOfByte = 1;
        this.primitiveOfLong = 2;
        this.primitiveOfChar = 3;
        this.wrapperOfInt = 4;
        this.wrapperOfByte = 5;
        this.wrapperOfLong = Long.valueOf(6);
        this.wrapperOfChar = 7;
        this.arr_primitiveOfInt = new int [] {8, 9};
        this.arr_primitiveOfByte = new byte [] {10,11};
        this.arr_primitiveOfLong = new long [] {12,13};
        this.arr_primitiveOfChar = new char []  {14,15};
        this.arr_wrapperOfInt = new Integer[]  {16,17};
        this.arr_wrapperOfByte = new Byte []  {18,19};
        this.arr_wrapperOfLong = new Long []  {Long.valueOf(20), Long.valueOf(21)};
        this.arr_wrapperOfChar = new Character[]  {22,23};
        this.objectOfCollection = new ArrayList();
        objectOfCollection.add("test1");
        objectOfCollection.add("test2");
        objectOfCollection.add("test3");
    }


    @Override
    public String toString() {
        return "AnyObject{" +
                "primitiveOfInt=" + primitiveOfInt +
                ", primitiveOfByte=" + primitiveOfByte +
                ", primitiveOfLong=" + primitiveOfLong +
                ", primitiveOfChar=" + primitiveOfChar +
                ", wrapperOfInt=" + wrapperOfInt +
                ", wrapperOfByte=" + wrapperOfByte +
                ", wrapperOfLong=" + wrapperOfLong +
                ", wrapperOfChar=" + wrapperOfChar +
                ", arr_primitiveOfInt=" + Arrays.toString(arr_primitiveOfInt) +
                ", arr_primitiveOfByte=" + Arrays.toString(arr_primitiveOfByte) +
                ", arr_primitiveOfLong=" + Arrays.toString(arr_primitiveOfLong) +
                ", arr_primitiveOfChar=" + Arrays.toString(arr_primitiveOfChar) +
                ", arr_wrapperOfInt=" + Arrays.toString(arr_wrapperOfInt) +
                ", arr_wrapperOfByte=" + Arrays.toString(arr_wrapperOfByte) +
                ", arr_wrapperOfLong=" + Arrays.toString(arr_wrapperOfLong) +
                ", arr_wrapperOfChar=" + Arrays.toString(arr_wrapperOfChar) +
                ", objectOfCollection=" + objectOfCollection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnyObject anyObject = (AnyObject) o;
        return primitiveOfInt == anyObject.primitiveOfInt &&
                primitiveOfByte == anyObject.primitiveOfByte &&
                primitiveOfLong == anyObject.primitiveOfLong &&
                primitiveOfChar == anyObject.primitiveOfChar &&
                Objects.equals(wrapperOfInt, anyObject.wrapperOfInt) &&
                Objects.equals(wrapperOfByte, anyObject.wrapperOfByte) &&
                Objects.equals(wrapperOfLong, anyObject.wrapperOfLong) &&
                Objects.equals(wrapperOfChar, anyObject.wrapperOfChar) &&
                Arrays.equals(arr_primitiveOfInt, anyObject.arr_primitiveOfInt) &&
                Arrays.equals(arr_primitiveOfByte, anyObject.arr_primitiveOfByte) &&
                Arrays.equals(arr_primitiveOfLong, anyObject.arr_primitiveOfLong) &&
                Arrays.equals(arr_primitiveOfChar, anyObject.arr_primitiveOfChar) &&
                Arrays.equals(arr_wrapperOfInt, anyObject.arr_wrapperOfInt) &&
                Arrays.equals(arr_wrapperOfByte, anyObject.arr_wrapperOfByte) &&
                Arrays.equals(arr_wrapperOfLong, anyObject.arr_wrapperOfLong) &&
                Arrays.equals(arr_wrapperOfChar, anyObject.arr_wrapperOfChar) &&
                Objects.equals(objectOfCollection, anyObject.objectOfCollection);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(primitiveOfInt, primitiveOfByte, primitiveOfLong, primitiveOfChar, wrapperOfInt, wrapperOfByte, wrapperOfLong, wrapperOfChar, objectOfCollection);
        result = 31 * result + Arrays.hashCode(arr_primitiveOfInt);
        result = 31 * result + Arrays.hashCode(arr_primitiveOfByte);
        result = 31 * result + Arrays.hashCode(arr_primitiveOfLong);
        result = 31 * result + Arrays.hashCode(arr_primitiveOfChar);
        result = 31 * result + Arrays.hashCode(arr_wrapperOfInt);
        result = 31 * result + Arrays.hashCode(arr_wrapperOfByte);
        result = 31 * result + Arrays.hashCode(arr_wrapperOfLong);
        result = 31 * result + Arrays.hashCode(arr_wrapperOfChar);
        return result;
    }
}
