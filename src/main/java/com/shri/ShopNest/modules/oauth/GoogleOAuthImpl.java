package com.shri.ShopNest.modules.oauth;

import com.shri.ShopNest.config.properties.GoogleOAuthProperties;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthImpl implements OAuth {
    private final GoogleOAuthProperties googleOAuthProperties;

    public GoogleOAuthImpl(GoogleOAuthProperties googleOAuthProperties) {
        this.googleOAuthProperties = googleOAuthProperties;
    }

    @Override
    public String getLoginUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + googleOAuthProperties.getClientId() +
                "&redirect_uri=" + googleOAuthProperties.getRedirectUri() +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&access_type=offline";
    }
}
