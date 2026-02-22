package com.shri.ShopNest.modules.oauth;

import com.shri.ShopNest.config.properties.GoogleOAuthProperties;
import com.shri.ShopNest.modules.jwt.JwtService;
import com.shri.ShopNest.modules.oauth.dto.GoogleTokenResp;
import com.shri.ShopNest.modules.oauth.dto.GoogleUser;
import com.shri.ShopNest.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class GoogleOAuthImpl extends AbstractOAuthProvider {
    private final GoogleOAuthProperties googleOAuthProperties;

    public GoogleOAuthImpl(GoogleOAuthProperties googleOAuthProperties,
                           UserService userService,
                           JwtService jwtService, @Value("${frontend.url}") String frontendUrl ) {
        super(userService, jwtService, frontendUrl);
        this.googleOAuthProperties = googleOAuthProperties;
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

    protected GoogleTokenResp exchangeCode(String code) {
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

    public GoogleUser fetchUser(String accessToken) {
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
