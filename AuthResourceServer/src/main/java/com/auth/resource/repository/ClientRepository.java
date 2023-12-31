package com.auth.resource.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.resource.model.AppUser;
import com.auth.resource.model.Client;

public interface ClientRepository extends JpaRepository<Client, String> {
	
	Optional<Client> findByClientId(String clientId);
	Optional<Client> findByClientIdAndUserId(String clientId, String userId);
	@Transactional
	void deleteByClientId(String clientId);
	
	List<Client> findByUserId(String userId);
	Optional<Client> findByUserIdAndClientName(String userId, String clientName);
}
