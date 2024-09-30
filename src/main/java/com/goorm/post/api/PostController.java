package com.goorm.post.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@RequestParam int boardId,
                                      @RequestParam int postId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myPost")
    public ResponseEntity<?> getMyPost() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myComment")
    public ResponseEntity<?> getMyComment() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myScrap")
    public ResponseEntity<?> getMyScrap() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/scrap{boardId}/{postId}")
    public ResponseEntity<?> scrapPost(@PathVariable int boardId,
                                       @PathVariable int postId) {
        return ResponseEntity.ok().build();
    }
}
