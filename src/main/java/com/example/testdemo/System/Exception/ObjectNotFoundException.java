package com.example.testdemo.System.Exception;

public class ObjectNotFoundException extends RuntimeException{
    public  ObjectNotFoundException(String objectName ){
        super("Could not find " + objectName );
    }
    public  ObjectNotFoundException(String objectName , Integer id ){
        super("Could not find " + objectName + "With Id " +id +"" );
    }
}
