package com.auth.client.service;

import java.util.Arrays;
import java.util.List;

import com.auth.library.dto.ClientCreationPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth.library.dto.ClientControllerResponse;
import com.auth.library.dto.SingleClientControllerResponse;
import com.auth.library.model.CommonClientDetails;
import com.auth.library.service.ClientService;

import reactor.core.publisher.Mono;

@Service
public class ClienServiceImpl implements ClientService {

	@Value("${resource.server.baseurl}")
	private String resourceServerDomain;
	
	@Autowired
	private WebClient webClient;
	
	@Override
	public String addClient(CommonClientDetails client) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClientExists(String clientId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeClient(String clientId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addClient(String userId, RegisteredClient.Builder registeredClient) throws Exception {
		registeredClient.clientId("dummy-id");
		RegisteredClient client = registeredClient.build();

		ClientCreationPayload payload = new ClientCreationPayload();
		payload.setClientSecret(client.getClientSecret());
		payload.setClientName(client.getClientName());

		StringBuffer scopes = new StringBuffer();
		client.getScopes().forEach(scope -> scopes.append(scope).append(","));
		scopes.deleteCharAt(scopes.length() - 1);
		payload.setScopes(scopes.toString());

		payload.setRedirectUris(StringUtils
				.arrayToCommaDelimitedString(
						client.getRedirectUris().toArray(
								new String[client.getRedirectUris().size()])
				)
		);
		payload.setUserId(userId);
		Mono<ClientCreationPayload> response = webClient.post()
				.uri(resourceServerDomain + "/api/user/" + userId + "/client")
				.body(Mono.just(payload), ClientCreationPayload.class)
				.retrieve()
				.bodyToMono(ClientCreationPayload.class);

		return response.block().getClientId();
	}

	@Override
	public List<CommonClientDetails> getAllClients(String userId) {
		Mono<ClientControllerResponse> response =  webClient.get()
												 .uri(resourceServerDomain + "/api/user/" + userId + "/client")
												 .retrieve()
												 .bodyToMono(ClientControllerResponse.class);
		System.out.println("Got response from resource server ...");
		return response.block().getData();
	}

	@Override
	public CommonClientDetails getClientById(String userId, String clientId) {
		Mono<SingleClientControllerResponse> response =  webClient.get()
				 // TODO need to change this hardcoded value 
				 .uri(resourceServerDomain + "/api/user/" + userId + "/client/" + clientId)
				 .retrieve()
				 .bodyToMono(SingleClientControllerResponse.class);

		return response.block().getClient();
	}

}
