package com.goorm.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(String id);

    boolean existsByEmail(String email);
    boolean existsById(String id);
}
