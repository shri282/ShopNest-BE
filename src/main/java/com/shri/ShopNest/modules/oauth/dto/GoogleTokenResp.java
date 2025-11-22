package com.shri.ShopNest.modules.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleTokenResp implements TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String idToken;
}
