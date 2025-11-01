package com.shri.ShopNest.modules.oauth;

public class OAuthFactory {
    public static OAuth getOAuthProvider(String providerName) {
        if (providerName == null) {
            throw new IllegalArgumentException("Provider name cannot be null");
        }

        return switch (providerName) {
            case "google" -> new GoogleOAuthImpl();
            case "facebook" -> new FacebookOAuthImpl();
            case "github" -> new GithubOAuthImpl();
            default -> throw new UnsupportedOperationException("Unsupported OAuth provider: " + providerName);
        };
    }
}
