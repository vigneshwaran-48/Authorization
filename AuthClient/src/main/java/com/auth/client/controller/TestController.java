package com.auth.client.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

	@GetMapping
	public String hello(@RegisteredOAuth2AuthorizedClient("auth-server-client") OAuth2AuthorizedClient client) {
		System.setProperty("access_token", client.getAccessToken().getTokenValue());
		System.out.println(client.getAccessToken().getTokenValue());
		return "hello";
	}
}
