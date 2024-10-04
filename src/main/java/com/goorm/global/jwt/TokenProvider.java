package com.goorm.global.jwt;

import com.goorm.global.api.response.ApiResponse;
import com.goorm.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private long accessTokenValidity = 1000 * 60 * 30;
    private long refreshTokenValidity = 1000 * 60 * 60 * 24 * 7;

    public String createAccessToken(User user) {
        return createToken(user, accessTokenValidity);
    }

    public String createRefreshToken(User user) {
        return createToken(user, refreshTokenValidity);
    }

    private String createToken(User user, long validity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getUserId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

}
