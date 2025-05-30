package com.store.soattechchallenge.utils.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus statusCode;
    private final LocalDateTime timestamp;
    private final UUID uuid;

    public CustomException(String message, HttpStatus statusCode, String errorCode, LocalDateTime timestamp, UUID uuid) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
        this.uuid = uuid;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getUuid() {
        return uuid;
    }
}
