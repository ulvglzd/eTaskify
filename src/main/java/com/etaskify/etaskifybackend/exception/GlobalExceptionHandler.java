package com.etaskify.etaskifybackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExists(AlreadyExistsException ex) {
        Map<String, Boolean> additionalDetails = ex.getAdditionalDetails();
        Map<String, Object> response = new HashMap<>();

        response.put("message", ex.getMessage());
        response.put("additionalDetails", additionalDetails);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
