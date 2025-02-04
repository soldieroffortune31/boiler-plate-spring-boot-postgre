package com.example.springpostgres.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.springpostgres.model.WebResponse;

import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraintViolationException(ConstraintViolationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(WebResponse.<String>builder().code(HttpStatus.BAD_REQUEST.value()).message(exception.getMessage()).build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> ApiException(ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatusCode())
            .body(WebResponse.<String>builder().code(exception.getStatusCode().value()).message(exception.getReason()).build());
    }

    @ExceptionHandler({JwtException.class, SecurityException.class})
    public ResponseEntity<WebResponse<String>> handleUnauthorized(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(WebResponse.<String>builder().code(HttpStatus.UNAUTHORIZED.value()).message(ex.getMessage()).build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<WebResponse<String>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(WebResponse.<String>builder().code(HttpStatus.UNAUTHORIZED.value()).message("Unauthorized").build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> genericException(Exception ex){
        System.out.println(ex.getMessage());
        System.out.println(ex.getStackTrace());
        System.out.println(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(WebResponse.<String>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Internal Server Error").build());
    }

}
