package com.darkzera.fetcher.dto;

import com.darkzera.fetcher.entity.enumerator.AuthSupplier;

import java.util.Map;

public class OAuth2UserDataFactory {

    public static OAuth2UserData getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {

        if (registrationId.equalsIgnoreCase(AuthSupplier.GOOGLE.toString())) {
            return new GoogleOAuth2UserData(attributes);
        }

        throw new RuntimeException("Sorry! Login with " + registrationId + " is not supported yet.");
    }

}
