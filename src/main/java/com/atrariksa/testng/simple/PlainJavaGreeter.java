package com.atrariksa.testng.simple;

public class PlainJavaGreeter {
    private String hello = "hello";
    public String greet(String input) {
        return hello + ", " +input;
    }
    public String greet() {
        return hello;
    }
}
