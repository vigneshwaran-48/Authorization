package com.auth.resource.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.resource.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String> {
	
	AppUser findByUserName(String userName);
	
	void deleteByUserName(String userName);
	
	Optional<AppUser> findByEmail(String email);
}
