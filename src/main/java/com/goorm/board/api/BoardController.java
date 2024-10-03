package com.goorm.board.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    @GetMapping("/boards")
    @Operation(
            summary = "게시판 목록 조회",
            description = "모든 게시판 목록을 조회합니다."
    )
    public ResponseEntity<?> getAllBoards() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/boards/{boardId}")
    @Operation(
            summary = "게시판 조회",
            description = "특정 게시판의 정보를 조회합니다."
    )
    public ResponseEntity<?> getBoardPosts(@PathVariable int boardId,
                                           @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/boards/{boardId}/upload")
    @Operation(
            summary = "게시글 업로드",
            description = "특정 게시판에 게시물을 업로드합니다."
    )
    public ResponseEntity<?> uploadPost(@PathVariable int boardId) {
        return ResponseEntity.ok().build();
    }



}
