package com.goorm.user.api;

import com.goorm.global.api.response.ApiResponse;
import com.goorm.global.api.response.TokenDto;
import com.goorm.global.application.CustomOauth2Service;
import com.goorm.user.api.dto.request.UserJoinRequestDto;
import com.goorm.user.api.dto.request.UserLoginRequestDto;
import com.goorm.user.application.UserService;
import com.goorm.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입/로그인", description = "회원가입, 로그인, 로그아웃, 회원탈퇴, 프로필 조회를 담당하는 api 그룹")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CustomOauth2Service customOauth2Service;

    @PostMapping("/join")
    @Operation(
            summary = "자체 회원가입",
            description = "자체 회원가입을 수행합니다."
    )
    public ResponseEntity<ApiResponse<?>> join(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        ApiResponse<?> join = userService.join(userJoinRequestDto);
        return ResponseEntity.status(join.getStatusCode()).body(join);
    }

    @PostMapping("/login")
    @Operation(
            summary = "자체 로그인",
            description = "자체 로그인을 수행합니다. accessToken, refreshToken을 발급하여 반환합니다."
    )
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        ApiResponse<?> login = userService.login(userLoginRequestDto);
        return ResponseEntity.status(login.getStatusCode()).body(login);
    }

    @PostMapping("/{provider}/token")
    @Operation(
            summary = "소셜 로그인",
            description = "소셜 로그인을 수행합니다. accessToken, refreshToken을 발급하여 반환합니다."

    )
    public ApiResponse<?> socialLogin(@PathVariable("provider") String provider,
                                                   @RequestParam("code") String code) {

        // 1. 토큰 발급
        TokenDto tokenDto = customOauth2Service.socialLogin(provider, code);

        // 2. 발급된 토큰으로 사용자 정보 조회
        Map<String, Object> userInfo = customOauth2Service.getUserInfo(tokenDto.getAccessToken());

        TokenDto response = userService.socialLogin(userInfo);


        return ApiResponse.success(200, "소셜 로그인 성공", response);
    }

    @GetMapping("/logout")
    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 수행합니다."
    )
    public ApiResponse<?> logout() {
        return ApiResponse.success(200, "로그아웃 성공", null);
    }

    @GetMapping("/deleteUser")
    @Operation(
            summary = "회원탈퇴",
            description = "회원탈퇴를 수행합니다."
    )
    public ResponseEntity<?> deleteUser(Principal principal) {
        ApiResponse<?> response = userService.deleteUserByPrincipal(principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/profile")
    @Operation(
            summary = "프로필 조회",
            description = "프로필을 조회합니다."
    )
    public ResponseEntity<?> profile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

}
