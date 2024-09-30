package com.goorm.user.api.dto.request;

public record UserLoginRequestDto(
        String id,
        String password
) {
}
