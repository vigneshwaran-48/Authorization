package com.auth.library.exception;

public class UserNotFoundException extends AppException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message, 400);
	}
}
