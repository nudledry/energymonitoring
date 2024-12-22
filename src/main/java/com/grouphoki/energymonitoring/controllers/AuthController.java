package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.RegistrationDto;
import com.grouphoki.energymonitoring.models.UserEntity;
import com.grouphoki.energymonitoring.services.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserEntityService userEntityService;

    public AuthController(UserEntityService userEntityService){
        this.userEntityService = userEntityService;
    }

    @GetMapping("/login")
    public String getLoginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model){
        UserEntity existingUserEmail = userEntityService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            result.rejectValue("email", null, "This email is already in use. Please use a different one.");
            return "redirect:/register?fail";
        }

        UserEntity existingUserUsername = userEntityService.findByUsername(user.getUsername());
        if (existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()){
            result.rejectValue("username", null, "This username is already taken. Please choose a different one.");
            return "redirect:/register?fail";
        }


        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }

        userEntityService.saveUserEntity(user);
        return "redirect:/register?success";
    }
}
