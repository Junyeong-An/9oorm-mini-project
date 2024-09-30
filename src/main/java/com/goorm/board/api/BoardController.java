package com.goorm.board.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    @GetMapping("/boards")
    public ResponseEntity<?> getAllBoards() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<?> getBoardPosts(@PathVariable int boardId,
                                           @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/boards/{boardId}/upload")
    public ResponseEntity<?> uploadPost(@PathVariable int boardId) {
        return ResponseEntity.ok().build();
    }



}
