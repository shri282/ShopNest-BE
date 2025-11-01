package com.shri.ShopNest.modules.oauth;

import org.springframework.stereotype.Component;

@Component
public class FacebookOAuthImpl implements OAuth {
    @Override
    public String getLoginUrl() {
        return "";
    }
}
