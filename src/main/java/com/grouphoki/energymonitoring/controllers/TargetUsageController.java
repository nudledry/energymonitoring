package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.TargetUsageDto;
import com.grouphoki.energymonitoring.models.TargetUsage;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.security.SecurityUtil;
import com.grouphoki.energymonitoring.services.TargetUsageService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TargetUsageController {

    private TargetUsageService targetUsageService;
    private UserEntityService userEntityService;

    @Autowired
    public TargetUsageController(TargetUsageService targetUsageService, UserEntityService userEntityService) {
        this.targetUsageService = targetUsageService;
        this.userEntityService = userEntityService;
    }

    @GetMapping("/dashboard/target-usage")
    public String listTarget(Model model){
        UserEntity user = new UserEntity();
        List<TargetUsageDto> targetUsageDto = targetUsageService.getAllTargetUsage();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userEntityService.findByUsername(username);
            model.addAttribute("user", user);

        }
        model.addAttribute("user", user);
        model.addAttribute("targetUsage", targetUsageDto);
        return "dashboard/target-usage";
    }

    @GetMapping("/target-usage/")
    public String TargetUsage(Model model){
        List<TargetUsageDto> targetUsageDto = targetUsageService.getAllTargetUsage();
        model.addAttribute("targetUsage", targetUsageDto);
        return "target-usage-list";
    }

    @GetMapping("/target-usage/{userId}/new")
    public String createTargetUsage(@PathVariable("userId") Long userId, Model model){
        TargetUsage targetUsage = new TargetUsage();
        model.addAttribute("userId", userId);
        model.addAttribute("targetUsage", targetUsage);
        return "target-usage-new";
    }

    @PostMapping("target-usage/{userId}")
    public String saveTargetUsage(@Valid @ModelAttribute("targetUsage") TargetUsageDto targetUsageDto, Model model){
        targetUsageService.saveTargetUsage(targetUsageDto);
        return "redirect:/user/";
    }

    @GetMapping("/target-usage/{targetUsageId}/edit")
    public String editTargetUsage(@PathVariable("targetUsageId") Long targetUsageId, Model model) {
        TargetUsageDto targetUsageDto = targetUsageService.findTargetById(targetUsageId);
        model.addAttribute("targetUsage", targetUsageDto);  // Changed from "targetUsages" to "targetUsage"
        return "target-usage-edit";
    }

}
