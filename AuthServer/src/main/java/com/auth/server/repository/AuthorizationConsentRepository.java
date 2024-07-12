package com.auth.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.server.model.AuthorizationConsent;

public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, String> {

	Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId,
			String principalName);

	void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
