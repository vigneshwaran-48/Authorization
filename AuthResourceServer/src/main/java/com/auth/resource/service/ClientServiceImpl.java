package com.auth.resource.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.auth.library.model.CommonClientDetails;
import com.auth.library.service.ClientService;
import com.auth.resource.model.Client;
import com.auth.resource.repository.ClientRepository;
import com.google.common.base.Preconditions;


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
	public String addClient(RegisteredClient registeredClient) throws Exception {
		Client addedClient = clientRepository.save(Client.toClient(registeredClient));
		if(addedClient != null) {
			return addedClient.getId();
		}
		throw new Exception("Can't create the client");
	}

	@Override
	public List<CommonClientDetails> getAllClients(String userId) {
		Preconditions.checkArgument(userId != null, "User id required");
		
		List<Client> clients = clientRepository.findByUserId(userId);
		return clients.stream()
					  .map(Client::toCommonClientDetails)
					  .collect(Collectors.toList());
	}

	@Override
	public CommonClientDetails getClientById(String userId, String clientId) {
		
		//TODO Need to get the client details with userId in condition
		Client client = clientRepository.findByClientId(clientId)
					.orElseThrow(() -> new IllegalArgumentException("Client id not exists"));
		
		return Client.toCommonClientDetails(client);
	}
}
