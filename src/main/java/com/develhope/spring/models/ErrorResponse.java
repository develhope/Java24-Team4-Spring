package com.develhope.spring.models;

public record ErrorResponse(String exception, String status, String message, String timestamp, String stackTrace) {
}
