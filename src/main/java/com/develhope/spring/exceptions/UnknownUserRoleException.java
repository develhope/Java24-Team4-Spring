package com.develhope.spring.exceptions;

public class UnknownUserRoleException extends RuntimeException{

    public UnknownUserRoleException(String message) {
        super(message);
    }
}
