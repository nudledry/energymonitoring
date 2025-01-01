package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.NewsDto;
import com.grouphoki.energymonitoring.models.News;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.NewsService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsController {

    private UserEntityService userEntityService;
    private NewsService newsService;

    @Autowired
    public NewsController(UserEntityService userEntityService, NewsService newsService) {
        this.userEntityService = userEntityService;
        this.newsService = newsService;
    }

    @GetMapping("/user/dashboard/news")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userNews(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            List<NewsDto> newsList = newsService.findAllNews();
            model.addAttribute("newsList", newsList);
        }

        return "user/news";
    }

    @GetMapping("/admin/dashboard/news")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminNews(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            List<NewsDto> newsList = newsService.findAllNews();
            model.addAttribute("newsList", newsList);
        }

        return "admin/news";
    }
}
