package com.auth.library.service;

import java.util.List;

import com.auth.library.exception.AppException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import com.auth.library.model.CommonClientDetails;

@Service
public interface ClientService {

	String addClient(CommonClientDetails client) throws Exception;
	
	boolean isClientExists(String clientId);
	
	void removeClient(String userId, String clientId);

	/**
	 * This method will return the client id not the table's primary key id
	 * @param registeredClient
	 * @return
	 * @throws Exception
	 */
	String addClient(String userId, RegisteredClient.Builder registeredClient) throws Exception;
	
	List<CommonClientDetails> getAllClients(String userId);
	
	CommonClientDetails getClientById(String userId, String clientId);
	void updateClient(String userId, CommonClientDetails commonClientDetails) throws AppException;
}
