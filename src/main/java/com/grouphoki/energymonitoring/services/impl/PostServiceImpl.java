package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.PostDto;
import com.grouphoki.energymonitoring.exception.ResourceNotFoundException;
import com.grouphoki.energymonitoring.models.Post;
import com.grouphoki.energymonitoring.repository.PostRepository;
import com.grouphoki.energymonitoring.services.PostService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserEntityService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserEntityService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return convertToDto(post);
    }

    @Override
    public void createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedBy(userService.getCurrentUser());
        postRepository.save(post);
    }

    @Override
    public PostDto convertToDto(Post post) {
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
