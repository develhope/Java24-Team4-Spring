package com.develhope.spring.exceptions;

public class NegativeIdException extends RuntimeException{
    public NegativeIdException(String message) {
        super(message);
    }
}
