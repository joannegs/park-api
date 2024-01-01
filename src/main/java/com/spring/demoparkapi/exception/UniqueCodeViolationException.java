package com.spring.demoparkapi.exception;

public class UniqueCodeViolationException extends RuntimeException {
    public UniqueCodeViolationException(String message) {
        super(message);
    }
}
