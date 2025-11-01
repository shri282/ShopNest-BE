package com.shri.ShopNest.modules.auth;

import com.shri.ShopNest.modules.user.dto.AuthRequest;
import com.shri.ShopNest.modules.user.dto.RegisterRequest;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.pojo.AuthenticatedUserResponse;
import jakarta.servlet.http.Cookie;

public interface AuthService {
    AuthenticatedUserResponse login(AuthRequest req);
    User register(RegisterRequest req);
    AuthenticatedUserResponse refreshToken(Cookie cookie);
}
