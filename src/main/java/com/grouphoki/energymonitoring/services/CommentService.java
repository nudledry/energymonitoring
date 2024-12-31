package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.CommentDto;
import com.grouphoki.energymonitoring.models.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> getPostComments(Long postId);
    void createComment(CommentDto commentDto);
    CommentDto convertToDto(Comment comment);
}
