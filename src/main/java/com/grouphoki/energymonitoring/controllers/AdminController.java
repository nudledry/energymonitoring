package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.RegistrationDto;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private UserEntityService userEntityService;
    private PasswordEncoder passwordEncoder;

    public AdminController(UserEntityService userEntityService, PasswordEncoder passwordEncoder) {
        this.userEntityService = userEntityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{userId}/edit")
    public String edit(@PathVariable Long userId, Model model) {
        UserEntity userEntity = userEntityService.findById(userId);
        model.addAttribute("user", userEntity);
        return "admin/edit";
    }

    @PostMapping("/{userId}/edit")
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
        return "redirect:/admin/dashboard?success";
    }

    @PostMapping("/{userId}/delete")
    public String delete(@PathVariable Long userId) {
        UserEntity existingUser = userEntityService.findById(userId);
        if (existingUser != null) {
            userEntityService.deleteUserEntity(existingUser);
            return "redirect:/admin/dashboard?deleted";
        }

        return "redirect:/admin/dashboard?userNotFound";
    }

    @GetMapping("/new")
    public String newAdminForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "admin/register";
    }

    @PostMapping("/save")
    public String createAdmin(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model) {
        UserEntity existingUserEmail = userEntityService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            result.rejectValue("email", null, "This email is already in use. Please use a different one.");
            return "redirect:/admin/new?fail";
        }

        UserEntity existingUserUsername = userEntityService.findByUsername(user.getUsername());
        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()){
            result.rejectValue("username", null, "This username is already taken. Please choose a different one.");
            return "redirect:/admin/new?fail";
        }


        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "admin/register";
        }

        userEntityService.saveUserEntity(user);
        return "redirect:/admin/dashboard?created";
    }



}
