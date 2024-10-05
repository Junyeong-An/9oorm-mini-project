package com.goorm.global.application;

import com.goorm.global.api.response.TokenDto;
import com.goorm.global.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class CustomOauth2Service {

    private final TokenProvider tokenProvider;
    @Value("${security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${security.oauth2.client.registration.naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token";

    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/auth";
    private final String NAVER_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/naver/auth";

    public CustomOauth2Service(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public TokenDto socialLogin(String provider, String code) {
        String tokenUrl;
        String clientId;
        String clientSecret;
        String redirectUri;

        if ("google".equalsIgnoreCase(provider)) {
            tokenUrl = GOOGLE_TOKEN_URL;
            clientId = GOOGLE_CLIENT_ID;
            clientSecret = GOOGLE_CLIENT_SECRET;
            redirectUri = GOOGLE_REDIRECT_URI;
        } else if ("naver".equalsIgnoreCase(provider)) {
            tokenUrl = NAVER_TOKEN_URL;
            clientId = NAVER_CLIENT_ID;
            clientSecret = NAVER_CLIENT_SECRET;
            redirectUri = NAVER_REDIRECT_URI;
        } else {
            throw new IllegalArgumentException("지원하지 않는 소셜 로그인 제공자입니다: " + provider);
        }

        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        // HTTP 요청 본문과 헤더를 포함한 HttpEntity 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            // POST 요청을 보내고 응답을 TokenDto로 변환
            ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, Map.class);

            // 응답에서 액세스 토큰과 리프레시 토큰 추출
            Map<String, Object> body = response.getBody();
            if (body != null && body.containsKey("access_token")) {
                String accessToken = (String) body.get("access_token");

                // 사용자 정보 추출 후 리프레시 토큰 생성
                // 가정: 사용자가 이미 가입된 사용자라고 가정하고, 실제로는 사용자 정보를 추출하고 user 객체를 활용해야 함
                String refreshToken = tokenProvider.createRefreshToken(); // User 객체를 활용해도 됨

                // TokenDto 반환
                return new TokenDto(accessToken, refreshToken);
            } else {
                System.out.println("Response body: " + response.getBody());
                throw new IllegalArgumentException("소셜 로그인에서 유효한 토큰을 받아오지 못했습니다.");
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error: " + e.getResponseBodyAsString());
            throw new IllegalArgumentException("소셜 로그인 토큰 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }


    public Map<String, Object> getUserInfo(String accessToken) {
        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 생성 (Bearer 토큰 추가)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // HttpEntity 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 사용자 정보 요청
        ResponseEntity<Map> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                entity,
                Map.class
        );

        // 응답에서 사용자 정보 추출
        Map<String, Object> userInfo = response.getBody();

        return userInfo;
    }





}
