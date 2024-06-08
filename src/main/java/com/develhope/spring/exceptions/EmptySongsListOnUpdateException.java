package com.develhope.spring.exceptions;

public class EmptySongsListOnUpdateException extends RuntimeException{

    public EmptySongsListOnUpdateException(String message) {
        super(message);
    }
}
