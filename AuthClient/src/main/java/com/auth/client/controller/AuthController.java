package com.auth.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	
	@Value("${authserver-client.baseurl}")
	private String frontendBaseUrl;
	
	@GetMapping("login")
	public String login() {
		return "/";
	}
	
	@GetMapping("/ping")
	public ResponseEntity<String> ping(Authentication authentication) {
		
		if(authentication == null || authentication.getName() == null) {
			return new ResponseEntity<String>("UnAuthenticated", HttpStatusCode.valueOf(401));
		}
		return new ResponseEntity<String>("Ok", HttpStatus.OK);
	}
	
	@GetMapping("/csrf")
	public CsrfToken csrfToken(CsrfToken token) {
		return token;
	}
}
