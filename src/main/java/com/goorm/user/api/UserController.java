package com.goorm.user.api;

import com.goorm.user.api.dto.request.UserJoinRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/join")
    public ResponseEntity<?> join(UserJoinRequestDto userJoinRequestDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(UserJoinRequestDto userJoinRequestDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deleteUser")
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        return ResponseEntity.ok().build();
    }

}
