package com.shri.ShopNest.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth.github")
public class GithubOAuthProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
