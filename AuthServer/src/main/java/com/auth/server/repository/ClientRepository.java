package com.auth.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.server.model.Client;

public interface ClientRepository extends JpaRepository<Client, String> {
	
	Optional<Client> findByClientId(String clientId);
	
	void deleteByClientId(String clientId);
}
