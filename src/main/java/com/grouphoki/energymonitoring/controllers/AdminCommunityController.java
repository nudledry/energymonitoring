package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.services.CommentService;
import com.grouphoki.energymonitoring.services.PostService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/community")
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

}
