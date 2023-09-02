package com.auth.library.exception;

public class ScopeExistsException extends AppException {
    public ScopeExistsException(String message, int status) {
        super(message, status, AppExceptionType.BAD_REQUEST);
    }
    public ScopeExistsException(String message) {
        super(message, 400, AppExceptionType.BAD_REQUEST);
    }
}
