package com.goorm.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import com.goorm.board.domain.Board;
import com.goorm.comment.domain.Comment;
import com.goorm.user.domain.User;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    private String title;

    private String content;

    private Integer votes;

    private boolean isAnonymous;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
