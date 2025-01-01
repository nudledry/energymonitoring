package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.PostDto;
import com.grouphoki.energymonitoring.exception.ResourceNotFoundException;
import com.grouphoki.energymonitoring.mapper.PostMapper;
import com.grouphoki.energymonitoring.models.Post;
import com.grouphoki.energymonitoring.repository.PostRepository;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.PostService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.persistence.EntityNotFoundException;
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
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return PostMapper.mapToPostDto(post);
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
    public void updatePost(PostDto postDto) {
        String username = SecurityUtil.getSessionUser();
        Post post = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getCreatedBy().getUsername().equals(username)) {  // Changed to get username from UserEntity
            throw new IllegalStateException("You are not authorized to update this post");
        }

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        String username = SecurityUtil.getSessionUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getCreatedBy().getUsername().equals(username)) {  // Changed to get username from UserEntity
            throw new IllegalStateException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }
}
