package com.akproductions.testframework;

import java.util.HashMap;

public class TestResults<String, Integer> extends HashMap
{

public void printResults()
{

    this.forEach((k,v) ->   System.out.println("test parameter:"+k+", test value:"+v));
}

}
