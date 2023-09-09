package com.auth.library.service;

import com.auth.library.exception.AppException;
import com.auth.library.exception.ScopeExistsException;
import com.auth.library.model.CommonClientDetails;
import com.auth.library.model.CommonScopeDetails;

import java.util.List;
import java.util.Optional;

public interface ScopeService {

    Optional<CommonScopeDetails> getScopesOfClient();
    Optional<CommonScopeDetails> getScope(String clientId, Long scopeId);
    Long createScope(CommonClientDetails client, CommonScopeDetails scopeDetails) throws AppException;
    Long deleteScope(String clientId, Long scopeId);
    boolean isScopePresent(String clientId, Long scopeId);
    List<Long> deleteAllScopesOfClient(String clientId);

    /**
     * Returns added scopes id only.
     * @param client
     * @param scopes
     * @return
     * @throws AppException
     */
    List<Long> checkAndScopes(CommonClientDetails client, List<CommonScopeDetails> scopes) throws AppException;
}
