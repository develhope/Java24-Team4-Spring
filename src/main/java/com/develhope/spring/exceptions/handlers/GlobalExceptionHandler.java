package com.develhope.spring.exceptions.handlers;

import com.develhope.spring.models.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.develhope.spring.exceptions.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgExceptions(IllegalArgumentException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
        return createErrorResponse(ex, HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoFieldsHaveBeenUpdatedException.class)
    public ResponseEntity<ErrorResponse> handleNoFieldsUpdatedExceptions(NoFieldsHaveBeenUpdatedException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultExceptions(EmptyResultException ex) {
        return createErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptySongsListOnUpdateException.class)
    public ResponseEntity<ErrorResponse> handleEmptySongsListOnUpdateExceptions(EmptySongsListOnUpdateException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserFieldsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserFieldsExceptions(InvalidUserFieldsException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NegativeIdException.class)
    public ResponseEntity<ErrorResponse> handleNegativeIdExceptions(NegativeIdException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownUserRoleException.class)
    public ResponseEntity<ErrorResponse> handleUnknownUserRoleExceptions(UnknownUserRoleException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception ex, HttpStatus status) {
        String message = ex.getMessage();
        String timestamp = this.timestamp;
        String stackTrace = Arrays.toString(ex.getStackTrace());

        ErrorResponse errorResponseEntity = new ErrorResponse(message, timestamp, stackTrace);

        return ResponseEntity.status(status).body(errorResponseEntity);
    }
}
