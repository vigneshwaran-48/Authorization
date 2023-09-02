package com.auth.library.dto;

public class ClientCreationPayload {

	private String redirectUris, scopes, clientName, grantTypes, clientAuthMethods, clientId,
				   clientSecret;

	public ClientCreationPayload(String redirectUris, String scopes, String clientName, String grantTypes,
			String clientAuthMethods, String clientId, String clientSecret) {
		super();
		this.redirectUris = redirectUris;
		this.scopes = scopes;
		this.clientName = clientName;
		this.grantTypes = grantTypes;
		this.clientAuthMethods = clientAuthMethods;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
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

	public String getClientAuthMethods() {
		return clientAuthMethods;
	}

	public void setClientAuthMethods(String clientAuthMethods) {
		this.clientAuthMethods = clientAuthMethods;
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
}
