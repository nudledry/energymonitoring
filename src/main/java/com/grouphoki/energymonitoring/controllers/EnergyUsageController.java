package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import com.grouphoki.energymonitoring.services.EnergyUsageService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EnergyUsageController {

    private EnergyUsageService energyUsageService;
    private UserEntityService userEntityService;

    @Autowired
    public EnergyUsageController(EnergyUsageService energyUsageService, UserEntityService userEntityService) {
        this.energyUsageService = energyUsageService;
        this.userEntityService = userEntityService;
    }

    @PostMapping("user/dashboard/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String saveEnergyUsage(@Valid @ModelAttribute("energyUsage") EnergyUsageDto energyUsageDto, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("energyUsage", energyUsageDto);
            return "user/dashboard";
        }
        energyUsageService.saveEnergyUsage(energyUsageDto);
        return "redirect:/user/dashboard";
    }
}
