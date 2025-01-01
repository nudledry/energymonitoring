package com.grouphoki.energymonitoring.mapper;

import com.grouphoki.energymonitoring.dto.PostDto;
import com.grouphoki.energymonitoring.models.Post;

public class PostMapper {
    public static PostDto mapToPostDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setCreatedBy(post.getCreatedBy().getUsername());
        dto.setCommentCount(post.getComments().size());
        return dto;
    }
}
