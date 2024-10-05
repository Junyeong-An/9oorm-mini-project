package com.goorm.board.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Builder
public class BoardDetailResponseDto {
    private Integer boardId;
    private String boardName;
    private Integer currentPage;
    private Integer totalPages;
    private Integer totalItems;
    private List<PostDetailDto> posts;

    @Getter
    @Builder
    public static class PostDetailDto {
        private Integer postId;
        private String postTitle;
        private String postContent;
        private String author;
        private Integer likes;
        private Integer comments;
        private String timestamp;
    }
}
