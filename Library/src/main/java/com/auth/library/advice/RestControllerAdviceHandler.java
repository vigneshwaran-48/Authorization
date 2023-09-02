package com.auth.library.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.library.exception.*;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestControllerAdviceHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleException(IllegalArgumentException ex, HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(400, 
												ex.getMessage(), 
												LocalDateTime.now(),
												request.getServletPath());
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleException(UserNotFoundException ex, HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), 
												ex.getMessage(), 
												LocalDateTime.now(),
												request.getServletPath());
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(UnAuthenticatedException.class)
	public ResponseEntity<?> handleException(UnAuthenticatedException ex, HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), 
												ex.getMessage(), 
												LocalDateTime.now(),
												request.getServletPath());
		return new ResponseEntity<ErrorResponse>(errorResponse, 
						HttpStatusCode.valueOf(ex.getStatus()));
	}
	
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<?> handleException(UserExistsException ex, HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), 
												ex.getMessage(), 
												LocalDateTime.now(),
												request.getServletPath());
		return new ResponseEntity<ErrorResponse>(errorResponse, 
				HttpStatusCode.valueOf(ex.getStatus()));
	}
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<?> handleException(UnAuthorizedException ex, HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), 
												ex.getMessage(), 
												LocalDateTime.now(),
												request.getServletPath());
		return new ResponseEntity<ErrorResponse>(errorResponse, 
				HttpStatusCode.valueOf(ex.getStatus()));
	}
	@ExceptionHandler(AppException.class)
	public ResponseEntity<?> handleException(AppException ex, HttpServletRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(),
				ex.getExceptionType() == AppExceptionType.INTERNAL_SERVER
						? ex.getExceptionType().getLabel() : ex.getMessage(),
				LocalDateTime.now(),
				request.getServletPath());
		return new ResponseEntity<ErrorResponse>(errorResponse,
				HttpStatusCode.valueOf(ex.getStatus()));
	}
		
	record ErrorResponse(int status, String error, LocalDateTime timestamp, String path) {}
}
