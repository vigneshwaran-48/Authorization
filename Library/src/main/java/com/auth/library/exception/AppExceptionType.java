package com.auth.library.exception;

public enum AppExceptionType {
    INTERNAL_SERVER("Internal server"),
    BAD_REQUEST("Bad request"),
    UNAUTHENTICATED("Unauthenticated"),
    UNAUTHORIZED("Unauthorized");

    private String label;
    AppExceptionType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
