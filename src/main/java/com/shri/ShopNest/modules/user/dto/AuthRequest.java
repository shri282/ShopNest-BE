package com.shri.ShopNest.modules.user.dto;

import com.shri.ShopNest.enums.UserRole;
import lombok.Data;

@Data
public class AuthRequest {
    String username;
    String password;
    UserRole role;
}
