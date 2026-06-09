package com.pmotracker.msproject.infrastructure.exception;

public class ReferenceConstraintException extends RuntimeException {
    public ReferenceConstraintException(String message) {
        super(message);
    }
}
