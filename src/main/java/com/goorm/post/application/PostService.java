package com.goorm.post.application;

import com.goorm.post.api.dto.response.BoardPostResponseDto;
import com.goorm.post.api.dto.response.PostDetailResponseDto;
import com.goorm.post.api.dto.response.PostDetailResponseDto.CommentDto;
import com.goorm.post.api.dto.response.ScrapPostResponseDto;
import com.goorm.post.domain.Post;
import com.goorm.post.domain.PostRepository;
import com.goorm.user.domain.User;
import com.goorm.user.domain.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDetailResponseDto getPost(int boardId, int postId) {
        Post post = postRepository.findByBoardIdAndId(boardId, postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        // 게시글의 상세 정보와 댓글 목록을 DTO로 변환하여 반환
        List<CommentDto> comments = post.getComments().stream()
                .map(comment -> CommentDto.builder()
                        .commentId(comment.getId())
                        .commentAuthor(comment.getUser().getName())
                        .commentContent(comment.getContent())
                        .build())
                .collect(Collectors.toList());

        return PostDetailResponseDto.builder()
                .boardId(boardId)
                .postId(postId)
                .authorId(post.getUser().getUserId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.getUser().getName())
                .timestamp(post.getCreatedAt().toString())
                .likes(post.getVotes())
                .commentsCount(post.getComments().size())
                .comments(comments)
                .build();
    }

    public List<BoardPostResponseDto> getMyPosts(Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<Post> posts = postRepository.findByUser_UserId(user.getUserId());

        return posts.stream()
                .map(post -> BoardPostResponseDto.builder()
                        .boardId(post.getBoard().getId())
                        .boardName(post.getBoard().getBoardName())
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .postContent(post.getContent())
                        .author(post.getUser().getName())
                        .likes(post.getVotes())
                        .comments(post.getComments().size())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ScrapPostResponseDto> getMyScraps(Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        List<Post> scraps = postRepository.findScrapsByUser_UserId(user.getUserId());

        return scraps.stream()
                .map(post -> ScrapPostResponseDto.builder()
                        .boardId(post.getBoard().getId())
                        .boardName(post.getBoard().getBoardName())
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .postContent(post.getContent())
                        .author(post.getUser().getName())
                        .likes(post.getVotes())
                        .comments(post.getComments().size())
                        .build())
                .collect(Collectors.toList());
    }

}
