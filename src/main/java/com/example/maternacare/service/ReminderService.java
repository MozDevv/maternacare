package com.example.maternacare.service;

import com.example.maternacare.model.Reminder;
import com.example.maternacare.model.User;
import com.example.maternacare.repository.ReminderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public List<Reminder> getDueToday() {
        return reminderRepository.findByDate(LocalDate.now());
    }

    public List<Reminder> getDueTodayForUser(User user) {
        if (user == null) return Collections.emptyList();
        return reminderRepository.findByUserAndDate(user, LocalDate.now());
    }

    public List<Reminder> getUpcomingForUser(User user) {
        if (user == null) return Collections.emptyList();
        return reminderRepository.findTop5ByUserAndDateGreaterThanEqualOrderByDateAsc(user, LocalDate.now());
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void checkDailyReminders() {
        // In a real system, send emails or push notifications here.
        // For now, we simply trigger the query so the data is up-to-date in the DB/context
        getDueToday();
    }
}
