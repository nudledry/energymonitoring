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
@PreAuthorize("hasRole('ROLE_USER')")
public class UserCommunityController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserEntityService userEntityService;

    @Autowired
    public UserCommunityController(PostService postService, CommentService commentService, UserEntityService userEntityService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userEntityService = userEntityService;
    }

    @GetMapping
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
    public String postDetail(@PathVariable Long id, Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            PostDto post = postService.getPost(id);
            model.addAttribute("post", post);
            model.addAttribute("comments", commentService.getPostComments(id));
            model.addAttribute("newComment", new CommentDto());
            model.addAttribute("isPostCreator", post.getCreatedBy().equals(username));
        }

        return "user/post";
    }

    @GetMapping("/post/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        PostDto post = postService.getPost(id);
        String username = SecurityUtil.getSessionUser();

        if (!post.getCreatedBy().equals(username)) {
            return "redirect:/user/dashboard/community";
        }

        model.addAttribute("user", userEntityService.findByUsername(username));
        model.addAttribute("post", post);
        return "user/editPost";
    }

    @PostMapping("/post/{id}/edit")
    public String updatePost(@PathVariable Long id, @Valid @ModelAttribute("post") PostDto postDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/editPost";
        }

        PostDto post = postService.getPost(id);
        String username = SecurityUtil.getSessionUser();

        if (!post.getCreatedBy().equals(username)) {
            return "redirect:/user/dashboard/community";
        }

        postDto.setId(id);
        postService.updatePost(postDto);
        return "redirect:/user/dashboard/community/post/" + id;
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        PostDto post = postService.getPost(id);
        String username = SecurityUtil.getSessionUser();

        if (post.getCreatedBy().equals(username)) {
            postService.deletePost(id);
        }

        return "redirect:/user/dashboard/community";
    }


    @PostMapping("/post/create")
    public String createPost(@Valid @ModelAttribute("newPost") PostDto postDto, BindingResult bindingResult, Model model) {
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

    @PostMapping("/post/{postId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        String username = SecurityUtil.getSessionUser();
        CommentDto comment = commentService.getComment(commentId);

        if (comment.getCreatedBy().equals(username)) {
            commentService.deleteComment(commentId);
        }

        return "redirect:/user/dashboard/community/post/" + postId;
    }
}