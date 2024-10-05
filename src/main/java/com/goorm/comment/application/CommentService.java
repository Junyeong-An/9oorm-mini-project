package com.goorm.comment.application;

import com.goorm.comment.api.dto.CommentResponseDto;
import com.goorm.comment.domain.Comment;
import com.goorm.comment.domain.CommentRepository;
import com.goorm.user.domain.User;
import com.goorm.user.domain.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<CommentResponseDto> getMyComments(Principal principal) {
        String email = principal.getName();

        // 사용자 정보 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<Comment> comments = commentRepository.findByUserId(user.getId());

        return comments.stream()
                .map(comment -> CommentResponseDto.builder()
                        .commentId(comment.getId())
                        .commentContent(comment.getContent())
                        .postId(comment.getPost().getId())
                        .postTitle(comment.getPost().getTitle())
                        .build())
                .collect(Collectors.toList());
    }


}
