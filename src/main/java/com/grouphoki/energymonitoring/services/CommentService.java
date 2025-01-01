package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.CommentDto;
import com.grouphoki.energymonitoring.models.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> getPostComments(Long postId);
    CommentDto getComment(Long id);

    void createComment(CommentDto commentDto);
    void deleteComment(Long id);
    void deleteAllCommentsFromPost(Long id);

    CommentDto convertToDto(Comment comment);

}



