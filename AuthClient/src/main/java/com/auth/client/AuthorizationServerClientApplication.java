package com.auth.client;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@ComponentScan( basePackages = { "com.auth.client", "com.auth.library.controller" })
@RestController
public class AuthorizationServerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerClientApplication.class, args);
	}

	@GetMapping("/logout-success")
	public void logoutSuccessRedirect(HttpServletResponse response) {
		System.out.println("Logged out ....");
	}
}
