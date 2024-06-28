package com.develhope.spring.exceptions.handlers;

import com.develhope.spring.models.ErrorResponse;
import com.develhope.spring.models.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.develhope.spring.exceptions.*;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedFileFormatException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedFileFormatExceptions(UnsupportedFileFormatException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PlaylistUpdateException.class)
    public ResponseEntity<ErrorResponse> handlePlaylistUpdateExceptions(PlaylistUpdateException ex) {
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MinIOFileUploadException.class)
    public ResponseEntity<ErrorResponse> handleMinIOFileUploadExceptions(MinIOFileUploadException ex) {
        return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityMappingException.class)
    public ResponseEntity<ErrorResponse> handleEntityMappingExceptions(EntityMappingException ex) {
        return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededExceptions(MaxUploadSizeExceededException ex) {
        return createErrorResponse(ex, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AudioStreamingFailedException.class)
    public ResponseEntity<ErrorResponse> handleAudioStreamingFailedExceptions(AudioStreamingFailedException ex) {
        return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultiUploadFailedException.class)
    public ResponseEntity<ErrorResponse> handleMultiUploadFailedException(MultiUploadFailedException ex){
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> handleMultipartExceptions(MultipartException ex){
        return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(Exception ex, HttpStatus status) {

        String exception = ex.getClass().getName().toString();
        String statusString = status.toString();
        String message =  ex.getMessage();
        String timestamp = this.timestamp;
        String stackTrace = Arrays.toString(ex.getStackTrace());

        ErrorResponse errorResponseEntity = new ErrorResponse(exception, statusString, message, timestamp, stackTrace);

        return ResponseEntity.status(status).body(errorResponseEntity);
    }
}
