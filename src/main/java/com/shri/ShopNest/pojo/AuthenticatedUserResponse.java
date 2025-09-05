package com.shri.ShopNest.pojo;

import com.shri.ShopNest.modules.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticatedUserResponse {
    private User user;
    private String token;
}
