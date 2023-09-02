package com.auth.resource.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

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

	public AppUser getProviderName() {
		return user;
	}

	public void setProviderName(AppUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", user=" + user + "]";
	}

}
