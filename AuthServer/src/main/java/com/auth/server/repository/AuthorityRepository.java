package com.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.server.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	Authority findByAuthority(String authority);
}
