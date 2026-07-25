package com.example.ordertracking.exception;

/**
 * Thrown when a requested resource (e.g. an order) cannot be found.
 * Handled by {@link GlobalExceptionHandler} and translated into a
 * 404 Not Found response.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
