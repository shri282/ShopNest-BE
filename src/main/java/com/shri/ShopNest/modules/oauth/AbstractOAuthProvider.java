package com.shri.ShopNest.modules.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shri.ShopNest.enums.UserRole;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.modules.jwt.JwtService;
import com.shri.ShopNest.modules.oauth.dto.OAuthUser;
import com.shri.ShopNest.modules.oauth.dto.TokenResponse;
import com.shri.ShopNest.modules.user.mapper.UserMapper;
import com.shri.ShopNest.modules.user.service.UserService;

import java.util.List;

public abstract class AbstractOAuthProvider implements OAuth {

    protected final UserService userService;
    protected final JwtService jwtService;
    protected final ObjectMapper mapper = new ObjectMapper();
    protected final String FE_URL;

    protected AbstractOAuthProvider(UserService userService, JwtService jwtService, String fEndUrl) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.FE_URL = fEndUrl;
    }

    protected abstract TokenResponse exchangeCode(String code);
    protected abstract OAuthUser fetchUser(String accessToken);

    @Override
    public String callback(String code) throws JsonProcessingException {
        TokenResponse token = exchangeCode(code);
        OAuthUser providerUser = fetchUser(token.getAccessToken());

        User user = userService.upsert(
                User.builder()
                        .username(providerUser.getName())
                        .email(providerUser.getEmail())
                        .pic(providerUser.getPicture())
                        .isOAuthUser(true)
                        .roles(List.of(UserRole.USER))
                        .build()
        );

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String userJson = mapper.writeValueAsString(UserMapper.toDto(user));

        return buildResponse(accessToken, escape(userJson));
    }

    protected String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    protected String buildResponse(String token, String userJson) {
        return """
            <!doctype html>
            <html><body>
            <script>
                const allowedOrigin = "%s";
                if (window.opener) {
                    window.opener.postMessage({ token: "%s", user: "%s" }, allowedOrigin);
                }
                window.close();
            </script>
            </body></html>
        """.formatted(FE_URL, token, userJson);
    }
}