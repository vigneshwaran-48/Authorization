package com.auth.server.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "user_provider")
public class UserProvider {

	@Id
	@UuidGenerator
	@Column(name = "provider_id")
	private String providerId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", user=" + user + "]";
	}

}
