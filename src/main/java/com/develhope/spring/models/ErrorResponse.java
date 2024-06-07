package com.develhope.spring.models;

public record ErrorResponse(String message, String timestamp, String stackTrace) {
}
