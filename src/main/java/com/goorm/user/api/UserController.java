package com.goorm.user.api;

import com.goorm.user.api.dto.request.UserJoinRequestDto;
import com.goorm.user.api.dto.request.UserLoginRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/join")
    @Operation(
            summary = "자체 회원가입",
            description = "자체 회원가입을 수행합니다."
    )
    public ResponseEntity<?> join(UserJoinRequestDto userJoinRequestDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(
            summary = "자체 로그인",
            description = "자체 로그인을 수행합니다. accessToken, refreshToken을 발급하여 반환합니다."
    )
    public ResponseEntity<?> login(UserLoginRequestDto userLoginRequestDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{provider}/token")
    @Operation(
            summary = "소셜 로그인",
            description = "소셜 로그인을 수행합니다. accessToken, refreshToken을 발급하여 반환합니다."

    )
    public ResponseEntity<?> socialLogin() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 수행합니다."
    )
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deleteUser")
    @Operation(
            summary = "회원탈퇴",
            description = "회원탈퇴를 수행합니다."
    )
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    @Operation(
            summary = "프로필 조회",
            description = "프로필을 조회합니다."
    )
    public ResponseEntity<?> profile() {
        return ResponseEntity.ok().build();
    }

}
