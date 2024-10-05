package com.goorm.user.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginRequestDto(
        @Schema(description = "사용자 ID", example = "jun123")
        String id,
        @Schema(description = "사용자 비밀번호", example = "password123")
        String password
) {
}
