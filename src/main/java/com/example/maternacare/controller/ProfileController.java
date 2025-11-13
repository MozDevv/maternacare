package com.example.maternacare.controller;

import com.example.maternacare.model.User;
import com.example.maternacare.repository.UserRepository;
import com.example.maternacare.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class ProfileController {

    private final UserService userService;
    private final UserRepository userRepository;

    public ProfileController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<User> user = userService.findByEmail(userDetails.getUsername());

        User user1 = user.get();
        model.addAttribute("user", user1);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails, User updatedUser, Model model) {
        Optional<User> user2 = userService.findByEmail(userDetails.getUsername());
        User user = user2.get();
        user.setFullName(updatedUser.getFullName());
        user.setDueDate(updatedUser.getDueDate());
        userRepository.save(user);
        model.addAttribute("user", user);
        model.addAttribute("success", "Profile updated successfully.");
        return "profile";
    }
}
