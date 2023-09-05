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
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ClientService clientService;
	
	private ClientAuthenticationMethod getClientAuth() {
		return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
	}
	
	@PostMapping
	public ResponseEntity<?> addClient(
			@RequestBody ClientCreationPayload payload,
			Principal principal) throws Exception {

		if(principal == null || principal.getName() == null) {
			throw new UnAuthenticatedException("User details not found");
		}
		List<String> redirectUris = Arrays.asList(payload.getRedirectUris().split(","));
		List<String> scopes = Arrays.asList(payload.getScopes().split(","));
				
		RegisteredClient.Builder client =
				RegisteredClient.withId(UUID.randomUUID().toString())
					.clientSecret(passwordEncoder.encode(payload.getClientSecret()))
					.clientAuthenticationMethod(getClientAuth())
					.clientName(payload.getClientName())
					.scopes(scope -> scopes.forEach(scope::add))
					.redirectUris(redirect -> redirectUris.forEach(redirect::add))
					.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);

		String clientId = clientService.addClient(principal.getName(), client);
		payload.setClientId(clientId);
		payload.setClientSecret("******");

		CommonClientDetails clientDetails = clientService.getClientById(principal.getName(), clientId);

		SingleClientControllerResponse response = new SingleClientControllerResponse();
		response.setClient(clientDetails);
		response.setMessage("success");
		response.setStatus(HttpStatus.CREATED.value());
		response.setPath("/api/user/" + principal.getName() + "/client");
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
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
