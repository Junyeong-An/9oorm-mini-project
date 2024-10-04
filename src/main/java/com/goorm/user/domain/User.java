package com.goorm.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import com.goorm.comment.domain.Comment;
import com.goorm.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    private String id;

    private String name;

    private String email;

    private String nickname;

    private String password;

    private Integer year;

    private String universityName;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @Builder
    private User(Integer userId, String id, String name, String email, String nickname, String password,
                 Integer year, String universityName) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.year = year;
        this.universityName = universityName;
    }

    public boolean isMatchPassword(String password) {
        return this.password.equals(password);
    }


}
