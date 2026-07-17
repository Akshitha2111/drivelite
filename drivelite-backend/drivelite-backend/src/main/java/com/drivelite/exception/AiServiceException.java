package com.drivelite.exception;

/**
 * Thrown whenever the Flask AI microservice cannot be reached,
 * returns an error, or returns no usable predictions.
 *
 * Caught centrally by GlobalExceptionHandler and converted into
 * a clean JSON error response for the frontend.
 */
public class AiServiceException extends RuntimeException {

    public AiServiceException(String message) {
        super(message);
    }

    public AiServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}