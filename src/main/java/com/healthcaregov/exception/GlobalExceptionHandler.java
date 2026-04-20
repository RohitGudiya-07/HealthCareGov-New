package com.healthcaregov.exception;

import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<java.util.Map<String,Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(java.util.Map.of("success", false, "message", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<java.util.Map<String,Object>> handleDuplicate(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(java.util.Map.of("success", false, "message", ex.getMessage()));
    }

    @ExceptionHandler(SlotUnavailableException.class)
    public ResponseEntity<java.util.Map<String,Object>> handleSlot(SlotUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(java.util.Map.of("success", false, "message", ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<java.util.Map<String,Object>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(java.util.Map.of("success", false, "message", "Invalid email or password"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<java.util.Map<String,Object>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(java.util.Map.of("success", false, "message", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<java.util.Map<String,Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(java.util.Map.of("success", false, "message", "An unexpected error occurred"));
    }
}
