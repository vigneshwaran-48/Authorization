package com.auth.client;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.auth.client", "com.auth.library.controller" })
public class AuthorizationServerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerClientApplication.class, args);
	}
}
