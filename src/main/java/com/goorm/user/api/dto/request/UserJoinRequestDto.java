package com.goorm.user.api.dto.request;

import com.goorm.user.domain.RoleType;
import com.goorm.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserJoinRequestDto(
        @Schema(description = "가입 연도", example = "2024")
        int year,

        @Schema(description = "대학교 이름", example = "성공회대")
        String universityName,

        @Schema(description = "사용자의 이름", example = "안준영")
        String name,

        @Schema(description = "사용자의 닉네임", example = "준영짱짱맨")
        String nickname,

        @Schema(description = "사용자의 이메일", example = "jun123@example.com")
        String email,

        @Schema(description = "사용자 ID", example = "jun123")
        String id,

        @Schema(description = "비밀번호", example = "password123")
        String password,

        @Schema(description = "비밀번호 확인", example = "password123")
        String checkPassword
) {
        public User toEntity() {
                return User.builder()
                        .year(this.year)
                        .universityName(this.universityName)
                        .name(this.name)
                        .nickname(this.nickname)
                        .email(this.email)
                        .id(this.id)
                        .password(this.password)
                        .roleType(RoleType.ROLE_USER)
                        .build();
        }
}
