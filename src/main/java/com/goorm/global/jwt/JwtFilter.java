package com.goorm.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // JWT 토큰을 추출
        String jwt = tokenProvider.resolveToken((HttpServletRequest) request);

        // 디버그 로그 추가
        logger.debug("JWT Token: " + jwt); // 토큰 값 출력

        // 토큰이 존재하고 유효한 경우
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // 인증 정보 추출
            Authentication authentication = tokenProvider.getAuthentication(jwt);

            // 인증 정보 출력
            logger.debug("Authentication: " + authentication);

            // SecurityContextHolder에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 필터 체인을 계속 진행
        chain.doFilter(request, response);
    }
}
