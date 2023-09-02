package com.auth.library.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;
	private int status;
	private AppExceptionType exceptionType;
	
	public AppException(String message, int status) {
		super(message);
		this.status = status;
	}
	public AppException(String message, int status, AppExceptionType exceptionType) {
		super(message);
		this.status = status;
		this.exceptionType = exceptionType;
	}
	
	public int getStatus() {
		return status;
	}

	public AppExceptionType getExceptionType() {
		return exceptionType;
	}
}
