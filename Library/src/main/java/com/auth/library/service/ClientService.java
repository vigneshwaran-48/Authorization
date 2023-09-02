package com.auth.library.service;

import java.util.List;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import com.auth.library.model.CommonClientDetails;

@Service
public interface ClientService {

	String addClient(CommonClientDetails client) throws Exception;
	
	boolean isClientExists(String clientId);
	
	void removeClient(String clientId);
	
	String addClient(RegisteredClient registeredClient) throws Exception;
	
	List<CommonClientDetails> getAllClients(String userId);
	
	CommonClientDetails getClientById(String userId, String clientId);
}
