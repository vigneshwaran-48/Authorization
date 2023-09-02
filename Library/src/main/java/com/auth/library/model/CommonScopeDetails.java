package com.auth.library.model;

public class CommonScopeDetails {
    private Long scopeId;
    private String scopeName;
    private CommonClientDetails client;

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public CommonClientDetails getClient() {
        return client;
    }

    public void setClient(CommonClientDetails client) {
        this.client = client;
    }
}
