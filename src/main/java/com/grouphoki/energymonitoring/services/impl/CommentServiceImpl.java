package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.CommentDto;
import com.grouphoki.energymonitoring.exception.ResourceNotFoundException;
import com.grouphoki.energymonitoring.models.Comment;
import com.grouphoki.energymonitoring.repository.CommentRepository;
import com.grouphoki.energymonitoring.repository.PostRepository;
import com.grouphoki.energymonitoring.services.CommentService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserEntityService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserEntityService userService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public List<CommentDto> getPostComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreatedBy(userService.getCurrentUser());
        comment.setPost(postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found")));
        commentRepository.save(comment);
    }

    @Override
    public CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setCreatedBy(comment.getCreatedBy().getUsername());
        dto.setPostId(comment.getPost().getId());
        return dto;
    }
}
