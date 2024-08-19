package com.zinko.service.exception;

public class ServerException extends ConsoleAppException {
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }


    public ServerException(String message) {
        super(message);
    }
}
