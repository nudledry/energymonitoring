package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.NewsDto;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.NewsService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/news")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminNewsController {
    private final NewsService newsService;
    private UserEntityService userEntityService;

    @Autowired
    public AdminNewsController(NewsService newsService, UserEntityService userEntityService) {
        this.newsService = newsService;
        this.userEntityService = userEntityService;
    }

    @GetMapping("/new")
    public String createNewsForm(Model model) {
        String username = SecurityUtil.getSessionUser();
        model.addAttribute("user", userEntityService.findByUsername(username));
        model.addAttribute("news", new NewsDto());
        return "admin/news/create";
    }

    @PostMapping("/create")
    public String createNews(@Valid @ModelAttribute("news") NewsDto newsDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("news", newsDto);
            return "admin/news/create";
        }

        newsService.createNews(newsDto);
        return "redirect:/admin/dashboard/news?newsCreated";
    }

    @GetMapping("/{id}/edit")
    public String editNewsForm(@PathVariable("id") Long id, Model model) {
        String username = SecurityUtil.getSessionUser();
        model.addAttribute("user", userEntityService.findByUsername(username));
        NewsDto news = newsService.findById(id);
        model.addAttribute("news", news);
        return "admin/news/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateNews(@PathVariable("id") Long id, @Valid @ModelAttribute("news") NewsDto newsDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("news", newsDto);
            return "admin/news/edit";
        }

        newsDto.setId(id);
        newsService.updateNews(newsDto);
        return "redirect:/admin/dashboard/news?newsUpdated";
    }

    @PostMapping("/{id}/delete")
    public String deleteNews(@PathVariable("id") Long id) {
        newsService.deleteNews(id);
        return "redirect:/admin/dashboard/news?newsDeleted";
    }
}