package com.amigoscode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//    }

//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException e) {
//        Map<String, Object> errorResponse = new LinkedHashMap<>();
//        errorResponse.put("timestamp", LocalDateTime.now());
//        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
//        errorResponse.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
//        errorResponse.put("message", e.getMessage()); // Only modify the message
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//    }
}
