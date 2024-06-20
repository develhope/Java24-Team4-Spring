package com.develhope.spring.models;

public record ErrorResponse(int Status, String message, String timestamp, String stackTrace) {
}
