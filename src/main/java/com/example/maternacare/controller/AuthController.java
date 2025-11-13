package com.example.maternacare.controller;

import com.example.maternacare.model.User;
import com.example.maternacare.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        // Ensure dueDate is parsed if empty
        if (user.getDueDate() == null) {
            user.setDueDate(LocalDate.now().plusWeeks(40));
        }
        userService.register(user);
        model.addAttribute("success", "Registration successful. Please log in.");
        return "login";
    }
}
