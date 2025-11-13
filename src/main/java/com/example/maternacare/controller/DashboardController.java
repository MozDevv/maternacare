package com.example.maternacare.controller;

import com.example.maternacare.model.User;
import com.example.maternacare.repository.UserRepository;
import com.example.maternacare.service.MilestoneService;
import com.example.maternacare.service.ReminderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final MilestoneService milestoneService;
    private final ReminderService reminderService;

    public DashboardController(UserRepository userRepository, MilestoneService milestoneService, ReminderService reminderService) {
        this.userRepository = userRepository;
        this.milestoneService = milestoneService;
        this.reminderService = reminderService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser == null) return "redirect:/login";
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
        int week = user != null ? milestoneService.calculateCurrentWeek(user.getDueDate()) : 0;

        model.addAttribute("user", user);
        model.addAttribute("week", week);
        model.addAttribute("dueReminders", reminderService.getDueTodayForUser(user));
        model.addAttribute("upcomingReminders", reminderService.getUpcomingForUser(user));
        model.addAttribute("milestones", milestoneService.getMilestonesForWeek(week));
        model.addAttribute("tips", com.example.maternacare.util.PregnancyTips.getTipsForWeek(week));
        return "dashboard";
    }
}
