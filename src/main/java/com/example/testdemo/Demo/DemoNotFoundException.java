package com.example.testdemo.Demo;

public class DemoNotFoundException extends  RuntimeException {
    public DemoNotFoundException(Integer id){

        super("Could not find demo with id "+ id + " :(");
    }
}

