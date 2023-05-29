package com.darkzera.fetcher.config.auth;

import com.darkzera.fetcher.entity.dto.AuthenticationUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.util.Assert;

public final class CustomAuthenticationSuccessHandler implements AuthenticationProvider {

    private final Log logger = LogFactory.getLog(getClass());

    private JwtDecoder jwtDecoder;

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter =
            new JwtAuthenticationConverter();
    public CustomAuthenticationSuccessHandler(){}
    public CustomAuthenticationSuccessHandler(JwtDecoder jwtDecoder) {
        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
        this.jwtDecoder = jwtDecoder;
    }

    // This is basicaly same as original but i had to add a new validation
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
        Jwt jwt = getJwt(bearer);
        AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
        var user = AuthenticationUser.claims(jwt.getClaims());
        Assert.isTrue(isValidDomain(user.emailDomain()), "Domain mail must be gmail" );
        token.setDetails(user);
        this.logger.debug("Authenticated token");
        return token;
    }

    private Jwt getJwt(BearerTokenAuthenticationToken bearer) {
        try {
            return this.jwtDecoder.decode(bearer.getToken());
        }
        catch (BadJwtException failed) {
            this.logger.debug("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        }
        catch (JwtException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public boolean isValidDomain(String email){
        if (email.contains("gmail")){
            return true;
        }
        return false;
    }



}