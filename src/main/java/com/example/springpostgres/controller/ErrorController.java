package com.example.springpostgres.controller;


import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
// import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.springpostgres.exception.FileStorageException;
import com.example.springpostgres.model.WebResponse;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

// import io.jsonwebtoken.JwtException;
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

    // @ExceptionHandler({JwtException.class, SecurityException.class})
    // public ResponseEntity<WebResponse<String>> handleUnauthorized(Exception ex) {
    //     System.out.println(ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    //         .body(WebResponse.<String>builder().code(HttpStatus.UNAUTHORIZED.value()).message(ex.getMessage()).build());
    // }

    // @ExceptionHandler(AccessDeniedException.class)
    // public ResponseEntity<WebResponse<String>> handleAccessDenied(AccessDeniedException ex) {
    //     System.out.println(ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    //         .body(WebResponse.<String>builder().code(HttpStatus.UNAUTHORIZED.value()).message("Unauthorized").build());
    // }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<WebResponse<String>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
            .body(WebResponse.<String>builder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).message(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()).build());
    }

    // @ExceptionHandler(AccessDeniedException.class)
    // public ResponseEntity<WebResponse<String>> handleFileNotFoundException(AccessDeniedException ex) {
    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    //         .body(WebResponse.<String>builder().code(HttpStatus.UNAUTHORIZED.value()).message("Unauthorized").build());
    // }
    



    // bisa pake cara ini jika validasi true or false (cara ini tidak detail menujukkan pada field/key yang mana)
    // Disini itu gak hanya boolean yang di validasi jika nilainya tidak sesuai, tapi bisa juga integer yang diisi string, maka akan masuk ke exception ini
    // Disini juga bisa ditambah validasi misal jika mau menambahkan message value harus boolen, harus interger, dll
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<WebResponse<String>> handleJsonParseException(HttpMessageNotReadableException ex){
        System.out.println("kesini kah ? ke exception");
        if(ex.getCause() instanceof MismatchedInputException mismatchedInputEx){

            List<Reference> path = mismatchedInputEx.getPath();

            if(!path.isEmpty()){

                String fieldName = path.get(0).getFieldName();
                String errorMessage = fieldName + ": Format data tidak valid" ;

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(WebResponse.<String>builder().code(HttpStatus.BAD_REQUEST.value()).message(errorMessage).build());
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(WebResponse.<String>builder().code(HttpStatus.BAD_REQUEST.value()).message("Terjadi kesalahan dalam membaca permintaan").build());
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<WebResponse<String>> handleFileStoreageException(FileStorageException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(WebResponse.<String>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage()).build());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFound(FileNotFoundException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN)
            .body("File Not Found");
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
