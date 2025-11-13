package com.example.maternacare.controller;

import com.example.maternacare.model.Milestone;
import com.example.maternacare.model.User;
import com.example.maternacare.repository.MilestoneRepository;
import com.example.maternacare.repository.UserRepository;
import com.example.maternacare.service.MilestoneService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MilestonesController {

    private final MilestoneRepository milestoneRepository;
    private final MilestoneService milestoneService;
    private final UserRepository userRepository;

    public MilestonesController(MilestoneRepository milestoneRepository, MilestoneService milestoneService, UserRepository userRepository) {
        this.milestoneRepository = milestoneRepository;
        this.milestoneService = milestoneService;
        this.userRepository = userRepository;
    }

    @GetMapping("/milestones")
    public String list(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        int week = 0;
        if (currentUser != null) {
            User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
            if (user != null) {
                week = milestoneService.calculateCurrentWeek(user.getDueDate());
            }
        }
        model.addAttribute("week", week);
        model.addAttribute("milestones", milestoneRepository.findAll());
        return "milestones";
    }

    @GetMapping("/milestones/add")
    public String addForm(Model model) {
        model.addAttribute("milestone", new Milestone());
        model.addAttribute("formAction", "/milestones/add");
        return "milestone_form";
    }

    @PostMapping("/milestones/add")
    public String add(@RequestParam Integer weekNumber,
                      @RequestParam String description,
                      @RequestParam(required = false) String tip) {
        Milestone m = new Milestone();
        m.setWeekNumber(weekNumber);
        m.setDescription(description);
        m.setTip(tip);
        milestoneRepository.save(m);
        return "redirect:/milestones";
    }

    @GetMapping("/milestones/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Milestone m = milestoneRepository.findById(id).orElse(null);
        if (m == null) return "redirect:/milestones";
        model.addAttribute("milestone", m);
        model.addAttribute("formAction", "/milestones/edit/" + id);
        return "milestone_form";
    }

    @PostMapping("/milestones/edit/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam Integer weekNumber,
                         @RequestParam String description,
                         @RequestParam(required = false) String tip) {
        Milestone m = milestoneRepository.findById(id).orElse(null);
        if (m == null) return "redirect:/milestones";
        m.setWeekNumber(weekNumber);
        m.setDescription(description);
        m.setTip(tip);
        milestoneRepository.save(m);
        return "redirect:/milestones";
    }

    @GetMapping("/milestones/delete/{id}")
    public String delete(@PathVariable Long id) {
        milestoneRepository.findById(id).ifPresent(milestoneRepository::delete);
        return "redirect:/milestones";
    }
}
