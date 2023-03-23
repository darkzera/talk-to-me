package com.darkzera.fetcher.dto;

import java.util.Map;

public abstract class OAuth2UserData {
    protected Map<String, Object> attributes;

    public OAuth2UserData(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

}
