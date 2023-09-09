package com.auth.resource.repository;

import com.auth.resource.model.Scope;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {

    Optional<Scope> findByScopeName(String scopeName);
    Optional<Scope> findByClientClientIdAndScopeName(String clientId, String scopeName);
    @Transactional
    List<Scope> deleteByClientClientId(String clientId);
}
