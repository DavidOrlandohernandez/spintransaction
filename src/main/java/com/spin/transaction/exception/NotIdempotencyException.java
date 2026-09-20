package com.spin.transaction.exception;

public class NotIdempotencyException extends RuntimeException {
    public NotIdempotencyException(String message) {
        super(message);
    }
}
