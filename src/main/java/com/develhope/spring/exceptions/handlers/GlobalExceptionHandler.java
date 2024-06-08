package com.develhope.spring.exceptions.handlers;

import com.develhope.spring.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgExceptions(IllegalArgumentException ex) {


        String message = ex.getMessage();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String stackTrace = Arrays.toString(ex.getStackTrace());

        ErrorResponse errorResponseEntity = new ErrorResponse(message, timestamp, stackTrace);

        return ResponseEntity.badRequest().body(errorResponseEntity);
    }
}