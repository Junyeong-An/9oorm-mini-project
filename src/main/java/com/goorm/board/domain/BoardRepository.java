package com.goorm.board.domain;

import com.goorm.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAllByOrderByIdDesc();
    Optional<Board> findByBoardName(String boardName);
    void deleteById(Integer Id);

    Page<Post> findPostsById(int Id, PageRequest of);
}
