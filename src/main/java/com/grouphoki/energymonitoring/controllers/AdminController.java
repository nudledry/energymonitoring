package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.services.EnergyUsageService;
import com.grouphoki.energymonitoring.services.NewsService;
import com.grouphoki.energymonitoring.services.UserEntityService;
import com.grouphoki.energymonitoring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    private final UserService userService;
    private EnergyUsageService energyUsageService;
    private UserEntityService userEntityService;
    private NewsService newsService;
    private PasswordEncoder passwordEncoder;

    public AdminController(EnergyUsageService energyUsageService, NewsService newsService, UserEntityService userEntityService, UserService userService) {
        this.energyUsageService = energyUsageService;
        this.newsService = newsService;
        this.userEntityService = userEntityService;
        this.userService = userService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @GetMapping("/admin/{userId}/edit")
    public String edit(@PathVariable Long userId, Model model) {
        UserEntity userEntity = userEntityService.findById(userId);
        model.addAttribute("user", userEntity);
        return "admin/edit";
    }

    @PostMapping("/admin/{userId}/edit")
    public String update(@PathVariable Long userId, @Valid @ModelAttribute("user") UserEntity user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "redirect:/admin/{userId}/edit";
        }

        UserEntity existingUser = userEntityService.findById(userId);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));;

        userEntityService.updateUserEntity(existingUser);
        model.addAttribute("userId", userId);
        return "redirect:/admin/{userId}/edit?success";
    }

    @PostMapping("/admin/{userId}/delete")
    public String delete(@PathVariable Long userId) {
        userEntityService.deleteUserEntity(userId);
        return "redirect:/admin/dashboard?success";
    }
}
