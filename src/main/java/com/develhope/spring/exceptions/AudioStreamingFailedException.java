package com.develhope.spring.exceptions;

public class AudioStreamingFailedException extends RuntimeException{
    public AudioStreamingFailedException(String message) {
        super(message);
    }
}
