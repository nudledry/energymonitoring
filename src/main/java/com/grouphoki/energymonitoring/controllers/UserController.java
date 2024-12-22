package com.grouphoki.energymonitoring.controllers;

import com.grouphoki.energymonitoring.dto.UserDto;
import com.grouphoki.energymonitoring.models.User;
import com.grouphoki.energymonitoring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public String listUsers(Model model){
        List<UserDto> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user/{userId}")
    public String showUser(@PathVariable("userId") Long userId, Model model){
        UserDto userDto = userService.findUserById(userId);
        model.addAttribute("user", userDto);
        return "user-show";
    }

    @GetMapping("/user/new")
    public  String createUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user-new";
    }

    @PostMapping("/user/new")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "user-new";
        }
        userService.saveUser(userDto);
        return "redirect:/user";
    }

    @GetMapping("/user/{userId}/edit")
    public String editUser(@PathVariable("userId") Long userId, Model model){
        UserDto user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("user/{userId}/edit")
    public String updateUser(@PathVariable("userId") Long userId, @Valid @ModelAttribute("user") UserDto userDto, BindingResult result){
        if(result.hasErrors()){
            return "user-edit";
        }
        userDto.setId(userId);
        userService.updateUser(userDto);
        return "redirect:/user";
    }

    @GetMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return "redirect:/user";
    }
}   
