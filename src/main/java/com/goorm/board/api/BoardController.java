package com.goorm.board.api;

import com.goorm.board.application.BoardService;
import com.goorm.global.api.response.ApiResponse;
import com.goorm.board.api.dto.response.BoardListResponseDto;
import com.goorm.board.api.dto.response.BoardDetailResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    @Operation(
            summary = "게시판 목록 조회",
            description = "모든 게시판 목록을 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<BoardListResponseDto>>> getAllBoards() {
        List<BoardListResponseDto> boards = boardService.getAllBoards();
        return ResponseEntity.ok(ApiResponse.success(200, "전체 게시판 조회 성공", boards));
    }

    @GetMapping("/boards/{boardId}")
    @Operation(
            summary = "게시판 조회",
            description = "특정 게시판의 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<BoardDetailResponseDto>> getBoardPosts(@PathVariable("boardId") int Id,
                                                                             @RequestParam(defaultValue = "1") int page) {
        BoardDetailResponseDto boardDetail = boardService.getBoardPosts(Id, page);
        return ResponseEntity.ok(ApiResponse.success(200, "개별 게시판 조회 성공", boardDetail));
    }
}

