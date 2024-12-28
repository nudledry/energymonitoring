package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import com.grouphoki.energymonitoring.models.EnergyUsage;
import com.grouphoki.energymonitoring.models.News;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.EnergyUsageService;
import com.grouphoki.energymonitoring.services.NewsService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {
    private EnergyUsageService energyUsageService;
    private UserEntityService userEntityService;
    private NewsService newsService;

    @Autowired
    public DashboardController(EnergyUsageService energyUsageService, UserEntityService userEntityService, NewsService newsService) {
        this.energyUsageService = energyUsageService;
        this.userEntityService = userEntityService;
        this.newsService = newsService;
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminDashboard(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            List<UserEntity> allUser = userEntityService.getAll();
            model.addAttribute("allUser", allUser);
        }

        return "admin/dashboard"; // View name for admin dashboard
    }

    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userDashboard(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            List<EnergyUsageDto> energyUsageDto = energyUsageService.getEnergyUsageByUser(username);
            model.addAttribute("energyUsage", energyUsageDto);

            Double totalEnergyUsage = user.getEnergyUsages().stream()
                    .mapToDouble(EnergyUsage::getUseAmount)
                    .sum();
            model.addAttribute("totalEnergyUsage", totalEnergyUsage);
        }

        EnergyUsageDto energyUsageNew = new EnergyUsageDto();
        model.addAttribute("energyUsageNew", energyUsageNew);

        return "user/dashboard";
    }

    @GetMapping("/user/dashboard/news")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userNews(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

            List<News> newsList = newsService.getAllNews();
            model.addAttribute("newsList", newsList);
        }

        return "user/news";
    }

}
