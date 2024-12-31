package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.PostDto;
import com.grouphoki.energymonitoring.dto.CommentDto;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.PostService;
import com.grouphoki.energymonitoring.services.CommentService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("user/dashboard/community")
public class CommunityController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserEntityService userEntityService;

    @Autowired
    public CommunityController(PostService postService,
                               CommentService commentService,
                               UserEntityService userEntityService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userEntityService = userEntityService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public String communityPage(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            model.addAttribute("posts", postService.getAllPosts());
            model.addAttribute("newPost", new PostDto());
        }

        return "user/community";
    }

    @GetMapping("/post/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String postDetail(@PathVariable Long id, Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            model.addAttribute("post", postService.getPost(id));
            model.addAttribute("comments", commentService.getPostComments(id));
            model.addAttribute("newComment", new CommentDto());
        }

        return "user/post";
    }

    @PostMapping("/post/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String createPost(@Valid @ModelAttribute("newPost") PostDto postDto,
                             BindingResult bindingResult,
                             Model model) {
        String username = SecurityUtil.getSessionUser();

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userEntityService.findByUsername(username));
            model.addAttribute("posts", postService.getAllPosts());
            return "user/community";
        }

        postService.createPost(postDto);
        return "redirect:/user/dashboard/community";
    }

    @PostMapping("/post/{postId}/comment")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String createComment(@PathVariable Long postId, @Valid @ModelAttribute("newComment") CommentDto commentDto, BindingResult bindingResult, Model model) {
        String username = SecurityUtil.getSessionUser();

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userEntityService.findByUsername(username));
            model.addAttribute("post", postService.getPost(postId));
            model.addAttribute("comments", commentService.getPostComments(postId));
            return "user/post";
        }

        commentDto.setPostId(postId);
        commentService.createComment(commentDto);
        return "redirect:/user/dashboard/community/post/" + postId;
    }
}