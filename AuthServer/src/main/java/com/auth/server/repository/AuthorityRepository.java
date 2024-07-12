package com.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.server.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByAuthority(String authority);
}
