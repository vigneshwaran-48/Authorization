package com.auth.resource.service;

import com.auth.library.exception.AppException;
import com.auth.library.exception.AppExceptionType;
import com.auth.library.exception.ScopeExistsException;
import com.auth.library.model.CommonClientDetails;
import com.auth.library.model.CommonScopeDetails;
import com.auth.library.service.ScopeService;
import com.auth.resource.model.Client;
import com.auth.resource.model.Scope;
import com.auth.resource.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ScopeServiceImpl implements ScopeService {
    @Autowired
    private ScopeRepository scopeRepository;
    @Override
    public Optional<CommonScopeDetails> getScopesOfClient() {
        return Optional.empty();
    }

    @Override
    public Optional<CommonScopeDetails> getScope(String clientId, Long scopeId) {
        return Optional.empty();
    }

    @Override
    public Long createScope(CommonClientDetails client, CommonScopeDetails scopeDetails) throws AppException {
        if(scopeRepository.findByClientClientIdAndScopeName(
                client.getClientId(), scopeDetails.getScopeName()).isPresent()) {
            throw new ScopeExistsException("Scope already present for this user");
        }
        Scope scope = new Scope();
        scope.setScopeName(scopeDetails.getScopeName());
        scope.setClient(Client.toClient(client));

        Scope addedScope = scopeRepository.save(scope);

        if(addedScope == null) {
            throw new AppException("Error while adding scope!", 500, AppExceptionType.INTERNAL_SERVER);
        }
        return addedScope.getScopeId();
    }

    @Override
    public Long deleteScope(String clientId, Long scopeId) {
        return null;
    }

    @Override
    public boolean isScopePresent(String clientId, Long scopeId) {
        return false;
    }

    @Override
    public List<Long> checkAndScopes(CommonClientDetails client, List<CommonScopeDetails> scopes) throws AppException {
        List<Long> addedScopes = new ArrayList<>();
        for (CommonScopeDetails scope : scopes) {
            if(scopeRepository.findByClientClientIdAndScopeName(
                    client.getClientId(), scope.getScopeName()).isEmpty()) {
                addedScopes.add(createScope(client, scope));
            }
        }
        return addedScopes;
    }
}
