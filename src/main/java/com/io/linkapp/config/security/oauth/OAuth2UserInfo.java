package com.io.linkapp.config.security.oauth;

public interface OAuth2UserInfo {
    String getProviderId();
    String getUsername();
    String getProvider();
}
