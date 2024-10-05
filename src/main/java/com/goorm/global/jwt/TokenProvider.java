package com.goorm.global.jwt;

import com.goorm.user.application.CustomUserDetailsService;
import com.goorm.user.domain.CustomUserDetails;
import com.goorm.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final CustomUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long accessTokenValidity = 1000 * 60 * 30; // 30분
    private long refreshTokenValidity = 1000 * 60 * 60 * 24 * 7; // 7일

    // 액세스 토큰 생성
    public String createAccessToken(User user) {
        return createToken(user, accessTokenValidity);
    }


    public String createRefreshToken() {
        return Jwts.builder()
                .setSubject("refresh-token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(key)
                .compact();
    }


    // 토큰 생성 로직
    private String createToken(User user, long validity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getUserId());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail()) // 토큰의 subject로 사용자의 ID 설정
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 추출


    // 토큰에서 클레임을 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7); // "Bearer " 제거하고 JWT만 반환
            return StringUtils.hasText(token) ? token : null;
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        // 토큰에서 유저 정보 추출
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String email = claims.getSubject();

        // 유저 정보를 불러와서 UserDetails 생성
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        // Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
