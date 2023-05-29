package com.darkzera.fetcher.entity.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class AuthenticationUser extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String email;

    private AuthenticationUser(String email) {
        super(Collections.emptyList());
        this.email = email;
    }

    public static AuthenticationUser withEmail(String email){
        return new AuthenticationUser(email);
    }

    public static AuthenticationUser claims(Map<String, Object> claims){
        return new AuthenticationUser(claims.get("email").toString());
    }

    public String emailDomain(){
        return this.email;
    }

    @Override
    public Object getCredentials() {
        return this.email;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }
}
