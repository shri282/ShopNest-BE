package com.shri.ShopNest.modules.oauth;

import com.shri.ShopNest.modules.oauth.FacebookOAuthImpl;
import com.shri.ShopNest.modules.oauth.GithubOAuthImpl;
import com.shri.ShopNest.modules.oauth.GoogleOAuthImpl;
import com.shri.ShopNest.modules.oauth.OAuth;
import org.springframework.stereotype.Component;

@Component
public class OAuthFactory {

    private final GoogleOAuthImpl googleOAuth;
    private final FacebookOAuthImpl facebookOAuth;
    private final GithubOAuthImpl githubOAuth;

    public OAuthFactory(GoogleOAuthImpl googleOAuth, FacebookOAuthImpl facebookOAuth, GithubOAuthImpl githubOAuth) {
        this.googleOAuth = googleOAuth;
        this.facebookOAuth = facebookOAuth;
        this.githubOAuth = githubOAuth;
    }

    public OAuth getOAuthProvider(String providerName) {
        return switch (providerName) {
            case "google" -> googleOAuth;
            case "facebook" -> facebookOAuth;
            case "github" -> githubOAuth;
            default -> throw new UnsupportedOperationException("Unsupported OAuth provider: " + providerName);
        };
    }
}