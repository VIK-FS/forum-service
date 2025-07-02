package ait.cohort5860.post.dto.exception;

import ait.cohort5860.accounting.dto.exception.InvalidDataException;
import ait.cohort5860.accounting.dto.exception.UserExistsException;
import ait.cohort5860.accounting.dto.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        errorResponse.put("message", "Validation failed");
        errorResponse.put("errors", errors);
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        log.warn("Validation failed: {}", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();

        String errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String propertyPath = violation.getPropertyPath().toString();
                    String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
                    return fieldName + ": " + violation.getMessage();
                })
                .collect(Collectors.joining(", "));

        errorResponse.put("message", "Validation failed");
        errorResponse.put("errors", errors);
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        log.warn("Constraint violation: {}", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid argument");
        errorResponse.put("error", ex.getMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        log.warn("Illegal argument: {}", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePostNotFoundException(
            PostNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Post not found");
        errorResponse.put("error", ex.getMessage() != null ? ex.getMessage() : "Post not found");
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());

        log.warn("Post not found: {}", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getReason());
        errorResponse.put("status", ex.getStatusCode().value());

        log.warn("Response status exception: {}", ex.getReason());

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserExistsException(
            UserExistsException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "User already exists");
        errorResponse.put("error", ex.getMessage());
        errorResponse.put("status", HttpStatus.CONFLICT.value());

        log.warn("User exists: {}", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(
            UserNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "User not found");
        errorResponse.put("error", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());

        log.warn("User not found: {}", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDataException(
            InvalidDataException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid data");
        errorResponse.put("error", ex.getMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        log.warn("Invalid data: {}", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
