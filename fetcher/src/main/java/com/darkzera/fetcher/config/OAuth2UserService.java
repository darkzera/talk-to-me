package com.darkzera.fetcher.config;

import com.darkzera.fetcher.dto.OAuth2UserData;
import com.darkzera.fetcher.dto.OAuth2UserDataFactory;
import com.darkzera.fetcher.entity.UserProfile;
import com.darkzera.fetcher.entity.enumerator.AuthSupplier;
import com.darkzera.fetcher.repository.UserRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class OAuth2UserService extends OidcUserService {


    private UserRepository userRepository;

    public OAuth2UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        Map<String, Object> attributes = user.getAttributes();
        Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());

        final OAuth2UserData authorizedUserFromService = OAuth2UserDataFactory.getOAuth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(),
                user.getAttributes());

        authorities.add(new SimpleGrantedAuthority(AuthSupplier.GOOGLE.name()));

        if (!userRepository.existsByEmail(authorizedUserFromService.getEmail())) {
           createProfileForNewUser(authorizedUserFromService);
        }

        var userInfo = OidcUserInfo.builder()
                .email(authorizedUserFromService.getEmail())
                .emailVerified(true)
                .nickname(user.getName())
                .emailVerified(true)
                .build();

        return new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo);

    }

    @Transactional
    private void createProfileForNewUser(OAuth2UserData newUser){
        var userProfile = new UserProfile();
        userProfile.setEmail(newUser.getEmail());
        userProfile.setAuthSupplier(AuthSupplier.GOOGLE);
        userRepository.save(userProfile);
    }

}

