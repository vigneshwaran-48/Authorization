package com.auth.library.exception;

public class UnAuthorizedException extends AppException {

	private static final long serialVersionUID = 1L;
	
	public UnAuthorizedException(String message) {
		super(message, 403);
	}
}
