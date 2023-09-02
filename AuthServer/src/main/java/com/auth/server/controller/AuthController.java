package com.auth.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
//	
	@PostMapping("/oauth2/authorize")
	public void login() {
		System.out.println("Post authorize invoked .......");
	}
}
