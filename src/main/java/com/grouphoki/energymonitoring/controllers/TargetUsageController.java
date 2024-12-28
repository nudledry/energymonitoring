package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TargetUsageController {
    UserEntityService userEntityService;

    public TargetUsageController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @PostMapping("/user/dashboard/target")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String newTarget(@ModelAttribute("user") @Valid UserEntity userEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/dashboard";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = userEntityService.findByUsername(auth.getName());
        currentUser.setTarget(userEntity.getTarget());
        userEntityService.saveTarget(currentUser);
        return "redirect:/user/dashboard";
    }
}
