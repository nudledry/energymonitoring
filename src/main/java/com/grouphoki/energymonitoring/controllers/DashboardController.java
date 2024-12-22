package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.services.TargetUsageService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private TargetUsageService targetUsageService;
    private UserEntityService userEntityService;

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN')")// Ensure only admins can access this
    public String adminDashboard() {
        return "admin/dashboard"; // View name for admin dashboard
    }

    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")  // Ensure only users can access this
    public String userDashboard() {
        return "user/dashboard"; // View name for user dashboard
    }
}
