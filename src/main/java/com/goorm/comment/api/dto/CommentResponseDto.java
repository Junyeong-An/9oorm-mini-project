package com.goorm.comment.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {

    private Integer commentId;
    private String commentContent;
    private Integer postId;
    private String postTitle;
    private String timestamp;

}
