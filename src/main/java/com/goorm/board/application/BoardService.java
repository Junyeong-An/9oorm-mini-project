package com.goorm.board.application;

import com.goorm.board.api.dto.response.BoardDetailResponseDto;
import com.goorm.board.api.dto.response.BoardListResponseDto;
import com.goorm.board.domain.Board;
import com.goorm.board.domain.BoardRepository;
import com.goorm.post.domain.Post;
import com.goorm.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    public List<BoardListResponseDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(board -> BoardListResponseDto.builder()
                        .boardId(board.getId())
                        .boardName(board.getBoardName())
                        .posts(board.getPosts().stream()
                                .map(post -> BoardListResponseDto.PostSummaryDto.builder()
                                        .postId(post.getId())
                                        .postTitle(post.getTitle())
                                        .postContent(post.getContent())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public BoardDetailResponseDto getBoardPosts(int boardId, int page) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        Page<Post> postPage = postRepository.findByBoardId(boardId, PageRequest.of(page - 1, 10));

        List<BoardDetailResponseDto.PostDetailDto> postDetails = postPage.stream()
                .map(post -> BoardDetailResponseDto.PostDetailDto.builder()
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .postContent(post.getContent())
                        .author(post.getUser().getName())
                        .likes(post.getVotes())
                        .comments(post.getComments().size())
                        .timestamp(post.getCreatedAt().toString())
                        .build())
                .collect(Collectors.toList());

        return BoardDetailResponseDto.builder()
                .boardId(board.getId())
                .boardName(board.getBoardName())
                .currentPage(page)
                .totalPages(postPage.getTotalPages())
                .totalItems((int) postPage.getTotalElements())
                .posts(postDetails)
                .build();
    }
}
