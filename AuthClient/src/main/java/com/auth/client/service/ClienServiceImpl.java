package com.auth.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
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
	public String addClient(RegisteredClient registeredClient) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommonClientDetails> getAllClients(String userId) {
		Mono<ClientControllerResponse> response =  webClient.get()
												 .uri(resourceServerDomain + "/api/user/" + userId + "/client")
												 .retrieve()
												 .bodyToMono(ClientControllerResponse.class);
		
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
