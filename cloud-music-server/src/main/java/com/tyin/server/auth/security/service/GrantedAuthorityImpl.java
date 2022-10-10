package com.tyin.server.auth.security.service;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Tyin
 * @date 2022/9/29 17:10
 * @description ...
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
