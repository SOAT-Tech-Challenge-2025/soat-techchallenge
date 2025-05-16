package com.store.soattechchallenge.identification.infrastructure.adapters.in.advice;

import com.store.soattechchallenge.utils.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", ex.getStatusCode().value());
        response.put("message", ex.getMessage());
        response.put("errorCode", ex.getErrorCode());
        response.put("timestamp", ex.getTimestamp().toString());
        response.put("uuid", ex.getUuid().toString());

        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}