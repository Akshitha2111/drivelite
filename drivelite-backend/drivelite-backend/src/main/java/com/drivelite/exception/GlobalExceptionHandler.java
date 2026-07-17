package com.drivelite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(
            ResponseStatusException ex) {

        Map<String, String> errors = new HashMap<>();

        errors.put("error", ex.getReason());

        return new ResponseEntity<>(errors, ex.getStatusCode());
    }

    @ExceptionHandler(VehicleNotAvailableException.class)
    public ResponseEntity<Map<String, String>> handleVehicleNotAvailable(
            VehicleNotAvailableException ex) {

        Map<String, String> errors = new HashMap<>();

        errors.put("error", ex.getMessage());

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AiServiceException.class)
    public ResponseEntity<Map<String, String>> handleAiServiceException(
            AiServiceException ex) {

        Map<String, String> errors = new HashMap<>();

        errors.put("error", ex.getMessage());

        return new ResponseEntity<>(
                errors,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }
}