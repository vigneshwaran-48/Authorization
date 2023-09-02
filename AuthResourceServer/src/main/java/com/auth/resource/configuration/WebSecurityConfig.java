package com.auth.resource.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	
	@Value("${authserver.baseurl}")
	private String authServerUrl;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf()
				.disable()
				.authorizeHttpRequests(request -> {
					request
					.requestMatchers("/test").permitAll()
					.requestMatchers(HttpMethod.POST, "/api/user").permitAll()
					.requestMatchers(HttpMethod.OPTIONS, "/api/user")
					.permitAll()
							.requestMatchers(HttpMethod.GET, "/api/user/{userId}/profile-image")
							.permitAll()
					.anyRequest().authenticated();
				})
				.oauth2ResourceServer(oauth2 -> oauth2.jwt())
				.build();
	}
	@Bean
	public JwtDecoder jwtDecoder() {
	    return JwtDecoders.fromIssuerLocation(authServerUrl);
	}
}
