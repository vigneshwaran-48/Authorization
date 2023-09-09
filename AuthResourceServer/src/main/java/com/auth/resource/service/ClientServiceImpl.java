package com.auth.resource.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.auth.library.exception.AppException;
import com.auth.library.exception.AppExceptionType;
import com.auth.library.exception.ClientExistsException;
import com.auth.library.exception.UserNotFoundException;
import com.auth.library.model.CommonScopeDetails;
import com.auth.library.model.CommonUserDetails;
import com.auth.library.service.AppUserService;
import com.auth.library.service.ScopeService;
import com.auth.resource.model.AppUser;
import com.auth.resource.model.Scope;
import com.auth.resource.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
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
	private TokenSettings tokenSettings;
	@Autowired
	private ClientSettings clientSettings;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private ScopeService scopeService;

	@Value("${app.default.scopes}")
	private String defaultScopes;
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
	public void removeClient(String userId, String clientId) {
		Assert.notNull(clientId, "Client id can't be null");
		scopeService.deleteAllScopesOfClient(clientId);
		clientRepository.deleteByClientId(clientId);
	}

	/**
	 * This method will return the client id not the table's primary key id
	 * @param registeredClient
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addClient(String userId, RegisteredClient.Builder registeredClient)
			throws Exception {

		CommonUserDetails appUser = appUserService.findByUserId(userId);
		if(appUser == null) {
			throw new UserNotFoundException("User not found for " + userId);
		}
		String clientId = UUID.randomUUID().toString();
		registeredClient
				.clientId("proapp-" + clientId.toString())
				.tokenSettings(tokenSettings)
				.clientSettings(clientSettings);

		Client client = Client.toClient(registeredClient.build());

		if(clientRepository.findByUserIdAndClientName(userId, client.getClientName()).isPresent()) {
			throw new ClientExistsException("Client already existing with this name");
		}
		client.setUser(AppUser.toAppUser(appUser));
		Client addedClient = clientRepository.save(client);

		if(addedClient != null) {
			if(!isDefaultScopes(addedClient.getScopes())) {
				System.out.println("Adding scopes to db ...");
				addScopes(addedClient, addedClient.getScopes());
			}
			return addedClient.getClientId();
		}
		throw new Exception("Can't create the client");
	}
	private void addScopes(Client client, String scopes) throws AppException {
		List<CommonScopeDetails> scopeDetails = Arrays
				.stream(scopes.split(","))
				.map(scope -> {
					CommonScopeDetails scopeDetail = new CommonScopeDetails();
					scopeDetail.setClient(Client.toCommonClientDetails(client));
					scopeDetail.setScopeName(scope);
					return scopeDetail;
				})
				.toList();
		scopeService.checkAndScopes(Client.toCommonClientDetails(client), scopeDetails);
	}
	private boolean isDefaultScopes(String clientScopes) {
		List<String> clientScopesList = List.of(clientScopes.split(","));
		List<String> defaultScopesList = List.of(defaultScopes.split(","));

		for(String scope : clientScopesList) {
			if(!defaultScopesList.contains(scope)) {
				return false;
			}
		}
		return true;
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
		
		Client client = clientRepository.findByClientIdAndUserId(clientId, userId)
					.orElseThrow(() -> new IllegalArgumentException("Client id not exists"));
		
		return Client.toCommonClientDetails(client);
	}

	@Override
	public void updateClient(String userId, CommonClientDetails clientDetails)
			throws AppException {
		Preconditions.checkArgument(clientDetails != null,
									"Client data can't be empty");
		Preconditions.checkArgument(userId != null && !userId.isBlank(),
				"User id can't be empty");

		Optional<Client> prevClient =
					clientRepository
							.findByClientIdAndUserId(
									clientDetails.getClientId(),
									userId);
		if(prevClient.isEmpty()) {
			throw new AppException("Client not exists", 400, AppExceptionType.BAD_REQUEST);
		}
		if(clientDetails.getClientName() != null) {
			if(clientRepository
					.findByUserIdAndClientName(userId, clientDetails.getClientName()).isPresent()) {
				throw new ClientExistsException("Client already existing with this name");
			}
		}

		clientDetails.setUserDetails(AppUser.toCommonUserDetails(prevClient.get().getUser()));
		Client newClient = Client.toClient(clientDetails);
		checkAndUpdateClient(prevClient.get(), newClient);

		//TODO Need to validate the client details before updating
		clientRepository.save(newClient);
	}

	private void checkAndUpdateClient(Client prevClient, Client newClient) {
		if(newClient.getId() == null) {
			newClient.setId(prevClient.getId());
		}
		if(newClient.getClientSecret() == null) {
			newClient.setClientSecret(prevClient.getClientSecret());
		}
		if(newClient.getClientIdIssuedAt() == null) {
			newClient.setClientIdIssuedAt(prevClient.getClientIdIssuedAt());
		}
		if(newClient.getClientName() == null) {
			newClient.setClientName(prevClient.getClientName());
		}
		if(newClient.getClientSettings() == null) {
			newClient.setClientSettings(prevClient.getClientSettings());
		}
		if(newClient.getAuthorizationGrantTypes() == null) {
			newClient.setAuthorizationGrantTypes(prevClient.getAuthorizationGrantTypes());
		}
		if(newClient.getClientAuthenticationMethods() == null) {
			newClient.setClientAuthenticationMethods(
					prevClient.getClientAuthenticationMethods());
		}
		if(newClient.getClientSecretExpiresAt() == null) {
			newClient.setClientSecretExpiresAt(prevClient.getClientSecretExpiresAt());
		}
		if(newClient.getRedirectUris() == null) {
			newClient.setRedirectUris(prevClient.getRedirectUris());
		}
		if(newClient.getScopes() == null) {
			newClient.setScopes(prevClient.getScopes());
		}
		if(newClient.getTokenSettings() == null) {
			newClient.setTokenSettings(prevClient.getTokenSettings());
		}
		if(newClient.getUser() == null) {
			newClient.setUser(prevClient.getUser());
		}
	}
}
