package com.example.maternacare.controller;

import com.example.maternacare.model.Reminder;
import com.example.maternacare.model.User;
import com.example.maternacare.repository.ReminderRepository;
import com.example.maternacare.repository.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class RemindersController {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    public RemindersController(ReminderRepository reminderRepository, UserRepository userRepository) {
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/reminders")
    public String list(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser == null) return "redirect:/login";
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
        model.addAttribute("reminders", user == null ? java.util.List.of() : reminderRepository.findByUser(user));
        return "reminders";
    }

    @GetMapping("/reminders/add")
    public String addForm(Model model) {
        model.addAttribute("reminder", new Reminder());
        model.addAttribute("formAction", "/reminders/add");
        return "reminder_form";
    }

    @PostMapping("/reminders/add")
    public String add(@AuthenticationPrincipal UserDetails currentUser,
                      @RequestParam String title,
                      @RequestParam(required = false) String description,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (currentUser == null) return "redirect:/login";
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
        if (user == null) return "redirect:/login";
        Reminder r = new Reminder();
        r.setTitle(title);
        r.setDescription(description);
        r.setDate(date);
        r.setUser(user);
        reminderRepository.save(r);
        return "redirect:/reminders";
    }

    @GetMapping("/reminders/edit/{id}")
    public String editForm(@PathVariable Long id, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser == null) return "redirect:/login";
        Reminder r = reminderRepository.findById(id).orElse(null);
        if (r == null) return "redirect:/reminders";
        // Optional: ensure the current user owns this reminder
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
        if (user == null || !r.getUser().getId().equals(user.getId())) {
            return "redirect:/reminders";
        }
        model.addAttribute("reminder", r);
        model.addAttribute("formAction", "/reminders/edit/" + id);
        return "reminder_form";
    }

    @PostMapping("/reminders/edit/{id}")
    public String update(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails currentUser,
                         @RequestParam String title,
                         @RequestParam(required = false) String description,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (currentUser == null) return "redirect:/login";
        Reminder r = reminderRepository.findById(id).orElse(null);
        if (r == null) return "redirect:/reminders";
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
        if (user == null || !r.getUser().getId().equals(user.getId())) {
            return "redirect:/reminders";
        }
        r.setTitle(title);
        r.setDescription(description);
        r.setDate(date);
        reminderRepository.save(r);
        return "redirect:/reminders";
    }

    @GetMapping("/reminders/delete/{id}")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser == null) return "redirect:/login";
        Reminder r = reminderRepository.findById(id).orElse(null);
        if (r != null) {
            User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
            if (user != null && r.getUser().getId().equals(user.getId())) {
                reminderRepository.delete(r);
            }
        }
        return "redirect:/reminders";
    }
}
