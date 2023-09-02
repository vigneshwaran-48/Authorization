package com.auth.library.exception;

public class UserExistsException extends AppException {

	private static final long serialVersionUID = 1L;

	public UserExistsException(String message) {
		super(message, 400);
	}
}
