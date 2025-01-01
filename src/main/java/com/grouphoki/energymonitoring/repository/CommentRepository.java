package com.grouphoki.energymonitoring.repository;

import com.grouphoki.energymonitoring.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    void deleteByPostId(Long postId);
}