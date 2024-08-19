package com.zinko.service.exception;

public class ConsoleAppException extends RuntimeException {

    public ConsoleAppException(String message) {
        super(message);
    }

    public ConsoleAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
