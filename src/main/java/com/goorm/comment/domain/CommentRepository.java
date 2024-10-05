package com.goorm.comment.domain;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByOrderByIdDesc();
    void deleteById(Integer id);

    Page<Comment> findCommentsById(int Id, PageRequest of);

    List<Comment> findByUserId(String id);
}
