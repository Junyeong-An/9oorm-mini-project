package com.goorm.user.application;

import com.goorm.global.api.response.ApiResponse;
import com.goorm.user.api.dto.request.UserJoinRequestDto;
import com.goorm.user.domain.User;
import com.goorm.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ApiResponse<?> join(UserJoinRequestDto userJoinRequestDto) {

        validateUserJoinRequest(userJoinRequestDto);

        User user = userJoinRequestDto.toEntity();
        userRepository.save(user);

        return ApiResponse.success(200, "회원가입 성공", null);
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
