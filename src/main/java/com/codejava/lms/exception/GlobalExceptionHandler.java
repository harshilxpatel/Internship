package com.codejava.lms.exception;

import com.codejava.lms.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   // Better than @ControllerAdvice
public class GlobalExceptionHandler {

    // Resource Not Found (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Duplicate Resource (409)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicate(DuplicateResourceException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Validation Error (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // IMPORTANT: Generic Exception (Swagger Safe)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex, HttpServletRequest request) {

        String path = request.getRequestURI();

        // Swagger ko block nahi karna
        if (path.contains("/api-docs") || path.contains("/swagger")) {
            throw new RuntimeException(ex);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("message", "Something went wrong");
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Common Response Builder
    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {

        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .data(null)
                .build();

        return new ResponseEntity<>(response, status);
    }
}