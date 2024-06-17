package com.develhope.spring.exceptions;

public class UnsupportedFileFormatException extends RuntimeException{
    public UnsupportedFileFormatException(String message) {
        super(message);
    }
}
