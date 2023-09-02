package com.auth.library.exception;

public class ClientExistsException extends AppException {
    public ClientExistsException(String message) {
        super(message, 400, AppExceptionType.BAD_REQUEST);
    }
    public ClientExistsException(String message, int status) {
        super(message, status);
    }

    public ClientExistsException(String message, int status, AppExceptionType exceptionType) {
        super(message, status, exceptionType);
    }
}
