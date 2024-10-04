package com.goorm.user.application;

import com.goorm.global.api.response.ApiResponse;
import com.goorm.global.jwt.TokenProvider;
import com.goorm.user.api.dto.request.UserJoinRequestDto;
import com.goorm.user.api.dto.request.UserLoginRequestDto;
import com.goorm.user.domain.User;
import com.goorm.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public ApiResponse<?> join(UserJoinRequestDto userJoinRequestDto) {

        validateUserJoinRequest(userJoinRequestDto);

        User user = userJoinRequestDto.toEntity();
        userRepository.save(user);

        return ApiResponse.success(200, "회원가입 성공", null);
    }

    public ApiResponse<?> login(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findById(userLoginRequestDto.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!user.isMatchPassword((userLoginRequestDto.password()))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        return ApiResponse.success(200, "로그인 성공",
                "accessToken: " + accessToken + ", refreshToken: " + refreshToken);
    }

    private void validateUserJoinRequest(UserJoinRequestDto userJoinRequestDto) {
        if (userRepository.existsByEmail(userJoinRequestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.existsById(userJoinRequestDto.id())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

}
