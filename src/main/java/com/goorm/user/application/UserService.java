package com.goorm.user.application;

import com.goorm.global.api.response.ApiResponse;
import com.goorm.global.api.response.TokenDto;
import com.goorm.global.jwt.TokenProvider;
import com.goorm.user.api.dto.request.UserJoinRequestDto;
import com.goorm.user.api.dto.request.UserLoginRequestDto;
import com.goorm.user.domain.User;
import com.goorm.user.domain.UserRepository;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
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
        String refreshToken = tokenProvider.createRefreshToken();

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ApiResponse.success(200, "로그인 성공",
                "accessToken: " + accessToken + ", refreshToken: " + refreshToken);
    }

    public TokenDto socialLogin(Map<String, Object> userInfo) {
        String email = (String) userInfo.get("email");

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            user = User.builder()
                    .email(email)
                    .name((String) userInfo.get("name"))
                    .id((String) userInfo.get("id"))
                    .build();
            userRepository.save(user);
        }
        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken();
        return new TokenDto(accessToken, refreshToken);

    }

    private void validateUserJoinRequest(UserJoinRequestDto userJoinRequestDto) {
        if (userRepository.existsByEmail(userJoinRequestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.existsById(userJoinRequestDto.id())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    @Transactional
    public ApiResponse<?> deleteUserByPrincipal(Principal principal) {

        String name = principal.getName();

        // 이메일로 유저를 찾음
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 유저 삭제
        userRepository.delete(user);

        return ApiResponse.success(200, "유저가 성공적으로 삭제되었습니다.", null);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입된 유저가 없습니다."));
    }

}
