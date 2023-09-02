package com.auth.library.dto;

public class ClientCreationPayload {

	private String redirectUris, scopes, clientName, grantTypes, clientId,
				   clientSecret, userId;

	public ClientCreationPayload(String redirectUris, String scopes, String clientName, String grantTypes,
			String clientAuthMethods, String clientId, String clientSecret, String userId) {
		super();
		this.redirectUris = redirectUris;
		this.scopes = scopes;
		this.clientName = clientName;
		this.grantTypes = grantTypes;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.userId = userId;
	}
	
	public ClientCreationPayload() {}

	public String getRedirectUris() {
		return redirectUris;
	}

	public void setRedirectUris(String redirectUris) {
		this.redirectUris = redirectUris;
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(String grantTypes) {
		this.grantTypes = grantTypes;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
