package com.goorm.post.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScrapPostResponseDto {
    private Integer boardId;
    private String boardName;
    private Integer postId;
    private String postTitle;
    private String postContent;
    private String author;
    private Integer likes;
    private Integer comments;
}
