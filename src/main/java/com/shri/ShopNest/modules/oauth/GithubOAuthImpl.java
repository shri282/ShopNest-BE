package com.shri.ShopNest.modules.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shri.ShopNest.config.properties.GithubOAuthProperties;
import com.shri.ShopNest.config.properties.GoogleOAuthProperties;
import com.shri.ShopNest.enums.UserRole;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.modules.jwt.JwtService;
import com.shri.ShopNest.modules.oauth.dto.GitHubTokenResponse;
import com.shri.ShopNest.modules.oauth.dto.GithubUser;
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
public class GithubOAuthImpl implements OAuth {
    private final GithubOAuthProperties githubOAuthProperties;
    private final UserService userService;
    private final JwtService jwtService;

    public GithubOAuthImpl(GithubOAuthProperties githubOAuthProperties,
                           JwtService jwtService, UserService userService) {
        this.githubOAuthProperties = githubOAuthProperties;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public String buildLoginUrl(String state) {
        return UriComponentsBuilder.fromUriString("https://github.com/login/oauth/authorize")
                .queryParam("client_id", githubOAuthProperties.getClientId())
                .queryParam("redirect_uri", githubOAuthProperties.getRedirectUri())
                .queryParam("scope", "read:user user:email")
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    @Override
    public String callback(String code) throws JsonProcessingException {
        GitHubTokenResponse resp = this.exchangeCodeForToken(code);
        GithubUser githubUser = this.getUserInfo(resp.getAccessToken());

        User user = userService.upsert(
                User.builder()
                        .username(githubUser.getName())
                        .email(githubUser.getEmail())
                        .password("oauth")
                        .roles(List.of(UserRole.valueOf("USER")))
                        .phNo("2342347834")
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

    private GitHubTokenResponse exchangeCodeForToken(String code) {
        RestTemplate rt = new RestTemplate();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", githubOAuthProperties.getClientId());
        body.add("client_secret", githubOAuthProperties.getClientSecret());
        body.add("redirect_uri", githubOAuthProperties.getRedirectUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String,String>> req = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp = rt.postForEntity("https://github.com/login/oauth/access_token", req, Map.class);
        Map<String,Object> map = resp.getBody();
        return GitHubTokenResponse
                .builder()
                .accessToken((String)map.get("access_token"))
                .scope((String)map.get("scope"))
                .tokenType((String)map.get("token_type"))
                .build();
    }

    public GithubUser getUserInfo(String accessToken) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> ent = new HttpEntity<>(headers);

        ResponseEntity<Map> resp = rt.exchange("https://api.github.com/user", HttpMethod.GET, ent, Map.class);
        Map<String, Object> map = resp.getBody();

        String email = (String) map.get("email");

        if (email == null) {
            ResponseEntity<List> emailsResp = rt.exchange("https://api.github.com/user/emails", HttpMethod.GET, ent, List.class);
            List<Map<String, Object>> emails = emailsResp.getBody();

            if (emails != null && !emails.isEmpty()) {
                email = emails.stream()
                        .filter(e -> Boolean.TRUE.equals(e.get("primary")) && Boolean.TRUE.equals(e.get("verified")))
                        .map(e -> (String) e.get("email"))
                        .findFirst()
                        .orElse((String) emails.get(0).get("email"));
            }
        }

        return GithubUser.builder()
                .email(email)
                .name((String) map.get("login"))
                .picture((String) map.get("avatar_url"))
                .build();
    }

}
