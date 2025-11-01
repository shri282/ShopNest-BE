package com.shri.ShopNest.modules.oauth;

import org.springframework.stereotype.Component;

@Component
public class GithubOAuthImpl implements OAuth {
    @Override
    public String buildLoginUrl(String state) {
        return "";
    }

    @Override
    public String callback(String code) {
        return null;
    }
}
