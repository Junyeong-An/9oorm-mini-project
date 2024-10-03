package com.goorm.post.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @GetMapping("/posts")
    @Operation(
            summary = "게시글 조회",
            description = "특정 게시판의 게시글을 조회합니다."
    )
    public ResponseEntity<?> getPosts(@RequestParam int boardId,
                                      @RequestParam int postId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myPost")
    @Operation(
            summary = "내가 쓴 게시글 조회",
            description = "내가 쓴 게시글을 조회합니다."
    )
    public ResponseEntity<?> getMyPost() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myComment")
    @Operation(
            summary = "내가 쓴 댓글 조회",
            description = "내가 쓴 댓글을 조회합니다."
    )
    public ResponseEntity<?> getMyComment() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myScrap")
    @Operation(
            summary = "내가 스크랩한 게시글 조회",
            description = "내가 스크랩한 게시글을 조회합니다."
    )
    public ResponseEntity<?> getMyScrap() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/scrap{boardId}/{postId}")
    @Operation(
            summary = "게시글 스크랩",
            description = "특정 게시글을 스크랩합니다."
    )
    public ResponseEntity<?> scrapPost(@PathVariable int boardId,
                                       @PathVariable int postId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/boards/{boardId}/{postId}/likes")
    @Operation(
            summary = "게시글 좋아요",
            description = "특정 게시글에 좋아요를 누릅니다."
    )
    public ResponseEntity<?> likePost(@PathVariable int boardId,
                                      @PathVariable int postId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/boards/{boardId}/{postId}/comment")
    @Operation(
            summary = "댓글 등록",
            description = "특정 게시글에 댓글을 등록합니다."
    )
    public ResponseEntity<?> createComment(@PathVariable int boardId,
                                           @PathVariable int postId) {
        return ResponseEntity.ok().build();
    }


}
