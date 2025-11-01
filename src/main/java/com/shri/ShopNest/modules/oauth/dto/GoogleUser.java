package com.shri.ShopNest.modules.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleUser {
    private String email;
    private String name;
    private String picture;
}
