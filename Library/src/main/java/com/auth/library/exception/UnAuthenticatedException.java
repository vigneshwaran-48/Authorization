package com.auth.library.exception;

public class UnAuthenticatedException extends AppException {

	private static final long serialVersionUID = 1L;

	public UnAuthenticatedException(int status, String message) {
		super(message, status);
	}
	public UnAuthenticatedException(String message) {
		super(message, 401);
	}
}
