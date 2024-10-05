package com.goorm.global;

import com.goorm.global.jwt.JwtFilter;
import com.goorm.global.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenprovider;

    private final String[] PERMIT_ALL_URLS = {
            "/kakao/token",
            "/google/token",
            "/auth",
            "/user/**",
            "/boards/**",
            "/posts/**",
            "/myPost",
            "/myComment",
            "swagger-ui/**",
            "v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PERMIT_ALL_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(tokenprovider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

