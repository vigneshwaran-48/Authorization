package com.auth.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.auth.library.model.CommonClientDetails;
import com.auth.library.service.ClientService;
import com.auth.server.model.Client;
import com.auth.server.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public String addClient(CommonClientDetails client) throws Exception {
		Assert.notNull(client, "Client can't be null");
		Client addedClient = clientRepository.save(Client.toClient(client));
		if(addedClient != null) {
			return addedClient.getId();
		}
		throw new Exception("Can't create the client");
	}

	@Override
	public boolean isClientExists(String clientId) {
		Assert.notNull(clientId, "Client id can't be null");
		return clientRepository.findByClientId(clientId).isPresent();
	}

	@Override
	public void removeClient(String clientId) {
		Assert.notNull(clientId, "Client id can't be null");
		clientRepository.deleteByClientId(clientId);
	}

	@Override
	public String addClient(String userId, RegisteredClient.Builder registeredClient) throws Exception {
		Client addedClient = clientRepository.save(Client.toClient(registeredClient.build()));
		if(addedClient != null) {
			return addedClient.getId();
		}
		throw new Exception("Can't create the client");
	}

	@Override
	public List<CommonClientDetails> getAllClients(String userId) {
		// TODO Should implement this
		return null;
	}

	@Override
	public CommonClientDetails getClientById(String userId, String clientId) {
		// TODO Should implement this
		return null;
	}
}
