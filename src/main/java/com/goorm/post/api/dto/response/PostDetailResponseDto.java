package com.goorm.post.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PostDetailResponseDto {

    private Integer postId;
    private Integer boardId;
    private String postTitle;
    private String postContent;
    private String author;
    private Integer authorId;
    private String timestamp;
    private Integer likes;
    private Integer commentsCount;
    private Integer scrapsCount;
    private List<CommentDto> comments;

    @Getter
    @Builder
    public static class CommentDto {
        private Integer commentId;
        private String commentAuthor;
        private String commentContent;
        private String timestamp;
    }
}
