package com.auth.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = { "com.auth.resource", "com.auth.library.controller" })
public class AuthorizationResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationResourceServerApplication.class, args);
	}
	
	@GetMapping("test")
	public void test() {
		throw new IllegalArgumentException("Test exception");
	}
}
