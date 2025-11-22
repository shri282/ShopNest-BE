package com.shri.ShopNest.modules.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shri.ShopNest.config.properties.GoogleOAuthProperties;
import com.shri.ShopNest.enums.UserRole;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.modules.jwt.JwtService;
import com.shri.ShopNest.modules.oauth.dto.GoogleTokenResp;
import com.shri.ShopNest.modules.oauth.dto.GoogleUser;
import com.shri.ShopNest.modules.user.mapper.UserMapper;
import com.shri.ShopNest.modules.user.service.UserService;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
public class GoogleOAuthImpl implements OAuth {
    private final GoogleOAuthProperties googleOAuthProperties;
    private final UserService userService;
    private final JwtService jwtService;

    public GoogleOAuthImpl(GoogleOAuthProperties googleOAuthProperties,
                           UserService userService,
                           JwtService jwtService) {
        this.googleOAuthProperties = googleOAuthProperties;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public String buildLoginUrl(String state) {
        return UriComponentsBuilder.fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", googleOAuthProperties.getClientId())
                .queryParam("redirect_uri", googleOAuthProperties.getRedirectUri())
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email profile")
                .queryParam("access_type", "offline")
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    @Override
    public String callback(String code) throws JsonProcessingException {
        GoogleTokenResp resp = this.exchangeCodeForToken(code);
        GoogleUser googleUser = this.getUserInfo(resp.getAccessToken());

        User user = userService.upsert(
                User.builder()
                        .username(googleUser.getName())
                        .email(googleUser.getEmail())
                        .pic(googleUser.getPicture())
                        .isOAuthUser(true)
                        .roles(List.of(UserRole.valueOf("USER")))
                        .build()
        );

        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(UserMapper.toDto(user));
        String accessToken = jwtService.generateAccessToken(user.getUsername());

        String html = """
            <!doctype html>
            <html>
            <head><meta charset="utf-8"></head>
            <body>
            <script>
              (function() {
                // IMPORTANT: set allowed origin exactly to frontendOrigin
                const allowedOrigin = "%s";
                if (window.opener) {
                  window.opener.postMessage({ token: "%s", user: "%s" }, allowedOrigin);
                }
                window.close();
              })();
            </script>
            <p>Authentication complete. You can close this window.</p>
            </body>
            </html>
            """.formatted("http://localhost:3001", accessToken, escapeForJS(userJson));

        return html;
    }

    private static String escapeForJS(String json) {
        return json.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
    }

    private GoogleTokenResp exchangeCodeForToken(String code) {
        RestTemplate rt = new RestTemplate();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", googleOAuthProperties.getClientId());
        body.add("client_secret", googleOAuthProperties.getClientSecret());
        body.add("redirect_uri", googleOAuthProperties.getRedirectUri());
        body.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String,String>> req = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp = rt.postForEntity("https://oauth2.googleapis.com/token", req, Map.class);
        Map<String,Object> map = resp.getBody();
        return GoogleTokenResp
                .builder()
                .accessToken((String)map.get("access_token"))
                .refreshToken((String)map.get("refresh_token"))
                .idToken((String)map.get("id_token"))
                .build();
    }

    public GoogleUser getUserInfo(String accessToken) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> ent = new HttpEntity<>(headers);
        ResponseEntity<Map> resp = rt.exchange("https://openidconnect.googleapis.com/v1/userinfo", HttpMethod.GET, ent, Map.class);
        Map<String,Object> map = resp.getBody();
        return GoogleUser
                .builder()
                .email((String)map.get("email"))
                .name((String)map.get("name"))
                .picture((String)map.get("picture"))
                .build();
    }
}
