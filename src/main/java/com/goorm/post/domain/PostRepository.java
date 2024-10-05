package com.goorm.post.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByBoardId(int boardId, PageRequest pageRequest);

    List<Post> findByUser_UserId(Integer userId);

    List<Post> findScrapsByUser_UserId(Integer userId);

    Optional<Post> findByBoardIdAndId(int boardId, int postId);
}

