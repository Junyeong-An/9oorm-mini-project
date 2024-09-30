package com.goorm.user.api.dto.request;

public record UserJoinRequestDto(
        int year,
        String universityName,
        String name,
        String nickname,
        String email,
        String id,
        String password,
        String checkPassword
) {
}
