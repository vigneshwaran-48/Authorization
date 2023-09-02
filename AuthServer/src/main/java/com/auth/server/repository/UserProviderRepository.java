package com.auth.server.repository;

import com.auth.server.model.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProviderRepository extends JpaRepository<UserProvider, String> {
}
