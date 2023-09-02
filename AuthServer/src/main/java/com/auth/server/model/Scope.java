package com.auth.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "scope")
public class Scope {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scope_id")
	private Long scopeId;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@Column(name = "scope_name", nullable = false)
	private String scopeName;

	public Long getScopeId() {
		return scopeId;
	}

	public void setScopeId(Long scopeId) {
		this.scopeId = scopeId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	@Override
	public String toString() {
		return "Scope [scopeId=" + scopeId + ", client=" + client + ", scopeName=" + scopeName + "]";
	}
	
}
