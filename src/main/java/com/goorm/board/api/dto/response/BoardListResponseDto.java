package com.goorm.board.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class BoardListResponseDto {
    private Integer boardId;
    private String boardName;
    private List<PostSummaryDto> posts;

    @Getter
    @Builder
    public static class PostSummaryDto {
        private Integer postId;
        private String postTitle;
        private String postContent;
    }
}
