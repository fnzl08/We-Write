package com.example.hanghaefinal.repository;

import com.example.hanghaefinal.model.Badge;
import com.example.hanghaefinal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findAllByUser(User user);

    void deleteAllByUser(User user);
}
