package com.goorm.post.api;

import com.goorm.comment.api.dto.CommentResponseDto;
import com.goorm.comment.application.CommentService;
import com.goorm.global.api.response.ApiResponse;
import com.goorm.post.api.dto.response.BoardPostResponseDto;
import com.goorm.post.api.dto.response.PostDetailResponseDto;
import com.goorm.post.api.dto.response.ScrapPostResponseDto;
import com.goorm.post.application.PostService;
import io.swagger.v3.oas.annotations.Operation;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/posts")
    @Operation(
            summary = "게시글 조회",
            description = "특정 게시판의 게시글을 조회합니다."
    )
    public ResponseEntity<ApiResponse<PostDetailResponseDto>> getPost(@RequestParam int boardId,
                                                                      @RequestParam int postId) {
        PostDetailResponseDto postDetail = postService.getPost(boardId, postId);
        return ResponseEntity.ok(ApiResponse.success(200, "게시글 조회 성공", postDetail));
    }

    @GetMapping("/myPost")
    @Operation(
            summary = "내가 쓴 게시글 조회",
            description = "내가 쓴 게시글을 조회합니다."
    )
    public ResponseEntity<ApiResponse<?>> getMyPosts(Principal principal) {
        List<BoardPostResponseDto> myPosts = postService.getMyPosts(principal);
        return ResponseEntity.ok(ApiResponse.success(200, "내가 쓴 글 조회 성공", myPosts));
    }

    @GetMapping("/myComment")
    @Operation(
            summary = "내가 쓴 댓글 조회",
            description = "사용자가 작성한 모든 댓글을 조회합니다."
    )
    public ResponseEntity<ApiResponse<?>> getMyComments(Principal principal) {
        List<CommentResponseDto> myComments = commentService.getMyComments(principal);
        return ResponseEntity.ok(ApiResponse.success(200, "내가 쓴 댓글 조회 성공", myComments));
    }


    @GetMapping("/myScrap")
    @Operation(
            summary = "내가 스크랩한 게시글 조회",
            description = "내가 스크랩한 게시글을 조회합니다."
    )
    public ResponseEntity<ApiResponse<?>> getMyScraps(Principal principal) {
        List<ScrapPostResponseDto> myScraps = postService.getMyScraps(principal);
        return ResponseEntity.ok(ApiResponse.success(200, "내 스크랩 조회 성공", myScraps));
    }

    @PostMapping("/scrap{boardId}/{postId}")
    @Operation(
            summary = "게시글 스크랩",
            description = "특정 게시글을 스크랩합니다."
    )
    public ResponseEntity<?> scrapPost(@PathVariable int boardId,
                                       @PathVariable int postId) {
        return ResponseEntity.ok(ApiResponse.success(200, "게시글 스크랩 성공", null));
    }

    @PostMapping("/boards/{boardId}/{postId}/likes")
    @Operation(
            summary = "게시글 좋아요",
            description = "특정 게시글에 좋아요를 누릅니다."
    )
    public ResponseEntity<?> likePost(@PathVariable int boardId,
                                      @PathVariable int postId) {
        return ResponseEntity.ok(ApiResponse.success(200, "게시글 좋아요 성공", null));
    }

    @PostMapping("/boards/{boardId}/{postId}/comment")
    @Operation(
            summary = "댓글 등록",
            description = "특정 게시글에 댓글을 등록합니다."
    )
    public ResponseEntity<?> createComment(@PathVariable int boardId,
                                           @PathVariable int postId) {
        return ResponseEntity.ok(ApiResponse.success(200, "게시글 댓글 등록 성공", null));
    }


}
