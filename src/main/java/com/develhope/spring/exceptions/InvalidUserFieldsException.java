package com.develhope.spring.exceptions;

public class InvalidUserFieldsException extends RuntimeException{

    public InvalidUserFieldsException(String message) {
        super(message);
    }
}
