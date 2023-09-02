package com.auth.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.server.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String> {
	
	AppUser findByUserName(String username);
		
	void deleteByUserName(String username);
	
	Optional<AppUser> findByEmail(String email);
}
