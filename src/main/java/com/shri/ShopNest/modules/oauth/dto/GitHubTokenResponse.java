package com.shri.ShopNest.modules.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GitHubTokenResponse implements TokenResponse {
    private String accessToken;
    private String scope;
    private String tokenType;
}
