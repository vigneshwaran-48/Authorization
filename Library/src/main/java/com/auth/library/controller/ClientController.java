package com.auth.library.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.library.dto.ClientControllerResponse;
import com.auth.library.dto.ClientCreationPayload;
import com.auth.library.dto.SingleClientControllerResponse;
import com.auth.library.exception.UnAuthenticatedException;
import com.auth.library.model.CommonClientDetails;
import com.auth.library.service.ClientService;
import com.auth.library.utils.AuthServerUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/{userId}/client")
public class ClientController {

	@Autowired
	private TokenSettings tokenSettings;
	@Autowired
	private ClientSettings clientSettings;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ClientService clientService;
	
	private ClientAuthenticationMethod getClientAuthForString(String clientAuthStr) {
		switch (clientAuthStr) {
			case "client_secret_basic":
				return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
			case "client_secret_post": 
				return ClientAuthenticationMethod.CLIENT_SECRET_POST;
		default:
			return ClientAuthenticationMethod.CLIENT_SECRET_JWT;
		}
	}
	
	@PostMapping
	public ResponseEntity<RegisteredClient> addClient(@RequestBody ClientCreationPayload payload) throws Exception {
		List<AuthorizationGrantType> authGrantTypes = Arrays.asList(payload.getGrantTypes().split(","))
							.stream()
							.map(AuthServerUtils::resolveAuthorizationGrantType)
							.collect(Collectors.toList());
		List<String> redirectUris = Arrays.asList(payload.getRedirectUris().split(","));
		List<String> scopes = Arrays.asList(payload.getScopes().split(","));
				
		RegisteredClient client = 
				RegisteredClient.withId(UUID.randomUUID().toString())
					.clientId(payload.getClientId())
					.clientSecret(passwordEncoder.encode(payload.getClientSecret()))
					.clientAuthenticationMethod(getClientAuthForString(payload.getClientAuthMethods()))
					.clientName(payload.getClientName())
					.scopes(scope -> scopes.forEach(scope::add))
					.redirectUris(redirect -> redirectUris.forEach(redirect::add))
					.authorizationGrantTypes(grantType -> authGrantTypes.forEach(grantType::add))
					.tokenSettings(tokenSettings)
					.clientSettings(clientSettings)
					.build();
		clientService.addClient(client);
		
		return ResponseEntity.of(Optional.of(client));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllClient(@PathVariable("userId") String userId, 
							Principal principal, HttpServletRequest request) throws UnAuthenticatedException {
		
		if(principal == null || principal.getName() == null || principal.getName().isEmpty()) {
			throw new UnAuthenticatedException("Not Authenticated");
		}
		List<CommonClientDetails> clients = clientService.getAllClients(userId);
		ClientControllerResponse clientResponse = 
					new ClientControllerResponse(HttpStatus.OK.value(), "success",
				LocalDateTime.now(), clients, request.getServletPath());
		
		return ResponseEntity.ok(clientResponse);
	}
	
	@GetMapping("{clientId}")
	public ResponseEntity<?> getClient(@PathVariable("userId") String userId,
						@PathVariable("clientId") String clientId, Principal principal,
						HttpServletRequest request) throws UnAuthenticatedException {
		
		if(principal == null || principal.getName() == null || principal.getName().isEmpty()) {
			throw new UnAuthenticatedException("Not Authenticated");
		}
		CommonClientDetails client = clientService.getClientById(userId, clientId);
		SingleClientControllerResponse clientResponse = 
				new SingleClientControllerResponse(HttpStatus.OK.value(), "success",
			LocalDateTime.now(), client, request.getServletPath());
	
	return ResponseEntity.ok(clientResponse);
	}
	
//	@GetMapping("{clientId}")
//	public ResponseEntity<RegisteredClient> getClientById(@PathVariable String clientId) {
//		RegisteredClient client = registeredClientRepository.findByClientId(clientId);
//		return ResponseEntity.of(Optional.of(client));
//	}
	
//	@GetMapping("{imageName}")
//	public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
//		System.out.println("Searchin for image");
//		UserProfileImage userImage = userImageRepository.findByImageName(imageName).orElseThrow();
//		return ResponseEntity.ok()
//							 .contentType(MediaType.valueOf(userImage.getType()))
//							 .body(userImage.getImageBytes());
//	}
}
