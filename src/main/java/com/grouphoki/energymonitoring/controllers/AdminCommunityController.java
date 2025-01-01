package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.CommentDto;
import com.grouphoki.energymonitoring.dto.PostDto;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.CommentService;
import com.grouphoki.energymonitoring.services.PostService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/dashboard/community")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminCommunityController {
    private final PostService postService;
    private final CommentService commentService;
    private final UserEntityService userEntityService;

    @Autowired
    public AdminCommunityController(PostService postService, CommentService commentService, UserEntityService userEntityService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userEntityService = userEntityService;
    }

    @GetMapping
    public String communityPage(Model model, @RequestParam(required = false) String error) {
        String username = SecurityUtil.getSessionUser();
        model.addAttribute("user", userEntityService.findByUsername(username));
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("newPost", new PostDto());

        if (error != null) {
            if (error.equals("delete-failed")) {
                model.addAttribute("errorMessage", "Failed to delete post. Please try again.");
            }
        }

        return "admin/community";
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        String username = SecurityUtil.getSessionUser();
        PostDto post = postService.getPost(id);

        model.addAttribute("user", userEntityService.findByUsername(username));
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.getPostComments(id));
        model.addAttribute("newComment", new CommentDto());
        model.addAttribute("isPostCreator", true);

        return "admin/post";
    }

    @PostMapping("/post/create")
    public String createPost(@Valid @ModelAttribute("newPost") PostDto postDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userEntityService.findByUsername(SecurityUtil.getSessionUser()));
            model.addAttribute("posts", postService.getAllPosts());
            return "admin/community";
        }

        postService.createPost(postDto);
        return "redirect:/admin/dashboard/community";
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        try {
            PostDto post = postService.getPost(id);
            postService.deletePost(id);
            return "redirect:/admin/dashboard/community";
        } catch (Exception e) {
            return "redirect:/admin/dashboard/community?error=delete-failed";
        }
    }

    @PostMapping("/post/{postId}/comment")
    public String createComment(@PathVariable Long postId,
                                @Valid @ModelAttribute("newComment") CommentDto commentDto,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return postDetail(postId, model);
        }

        commentDto.setPostId(postId);
        commentService.createComment(commentDto);
        return "redirect:/admin/dashboard/community/post/" + postId;
    }

    @PostMapping("/post/{postId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/admin/dashboard/community/post/" + postId;
    }

    @PostMapping("/post/{id}/comments/delete-all")
    public String deleteAllComments(@PathVariable Long id) {
        commentService.deleteAllCommentsFromPost(id);
        return "redirect:/admin/dashboard/community/post/" + id;
    }

    @PostMapping("/delete-all")
    public String deleteAllPosts() {
        postService.deleteAllPosts();
        return "redirect:/admin/dashboard/community";
    }
}