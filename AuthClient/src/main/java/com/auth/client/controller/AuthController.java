package com.auth.client.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
	
	@Value("${authserver-client.baseurl}")
	private String frontendBaseUrl;
	
	private String authServerBaseUrl;
//
//	@GetMapping("/login/oauth2/authorized/auth-server-client")
//	private void login(@RequestParam String code, @RequestHeader(value = "Referer", required = false) String referrer) {
//		System.out.println("Authenticated ...........");
//		System.out.println("Code is => " + code);
//		System.out.println("Referer => " + referrer );
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
//		System.out.println(authToken);
//	}
	
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
