package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.EnergyUsageDto;
import com.grouphoki.energymonitoring.models.EnergyUsage;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.services.EnergyUsageService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String saveEnergyUsage(@Valid @ModelAttribute("energyUsage") EnergyUsageDto energyUsageDto,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("energyUsage", energyUsageDto);
            return "user/dashboard";
        }
        energyUsageService.saveEnergyUsage(energyUsageDto);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/dashboard/edit/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userEntityService.findByUsername(auth.getName());

        EnergyUsage energyUsage = energyUsageService.findById(id);

        if (!energyUsage.getCreatedBy().getId().equals(user.getId())) {
            return "redirect:/user/dashboard?error";
        }

        model.addAttribute("user", user);
        model.addAttribute("energyUsage", energyUsage);
        return "user/editEnergyUsage";
    }

    @PostMapping("/user/dashboard/edit/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String updateEnergyUsage(@PathVariable Long id, @Valid @ModelAttribute("energyUsage") EnergyUsageDto energyUsageDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/dashboard/edit";
        }
        energyUsageService.updateEnergyUsage(id, energyUsageDto);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/dashboard/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String deleteEnergyUsage(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userEntityService.findByUsername(auth.getName());

        EnergyUsage energyUsage = energyUsageService.findById(id);

        // Check if usage belongs to current user
        if (!energyUsage.getCreatedBy().getId().equals(user.getId())) {
            return "redirect:/user/dashboard?error";
        }

        energyUsageService.deleteEnergyUsage(id);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/dashboard/delete-all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String deleteAllEnergyUsage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userEntityService.findByUsername(auth.getName());

        energyUsageService.deleteAllByUser(user);
        return "redirect:/user/dashboard";
    }
}

