package com.shri.ShopNest.modules.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleTokenResp {
    private String accessToken;
    private String refreshToken;
    private String idToken;
}
