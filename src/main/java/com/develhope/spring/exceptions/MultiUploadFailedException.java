package com.develhope.spring.exceptions;

public class MultiUploadFailedException extends RuntimeException {
    public MultiUploadFailedException(String message) {
        super(message);
    }
}
