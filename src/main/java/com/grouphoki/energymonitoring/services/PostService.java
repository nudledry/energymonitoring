package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.PostDto;
import com.grouphoki.energymonitoring.models.Post;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto getPost(Long id);
    void createPost(PostDto postDto);
    void updatePost(PostDto postDto);
    void deletePost(Long id);
    void deleteAllPosts();
}
