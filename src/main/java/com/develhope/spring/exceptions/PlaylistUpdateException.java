package com.develhope.spring.exceptions;

public class PlaylistUpdateException extends RuntimeException{
    public PlaylistUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
