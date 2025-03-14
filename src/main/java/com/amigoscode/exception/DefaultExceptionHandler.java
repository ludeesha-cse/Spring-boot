package com.amigoscode.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handle(ResourceNotFoundException e, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(),
                List.of()
        );
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicateResourceException.class})
    public ResponseEntity<ApiError> handle(DuplicateResourceException e, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                ZonedDateTime.now(),
                List.of()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handle(MethodArgumentNotValidException e, HttpServletRequest request){
//        List<String> err = e.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
        String errorMessage = e.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                errorMessage,
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                List.of()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiError> handle(Exception e, HttpServletRequest request){
        System.out.println(e.getClass().getSimpleName());
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ZonedDateTime.now(),
                List.of()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
