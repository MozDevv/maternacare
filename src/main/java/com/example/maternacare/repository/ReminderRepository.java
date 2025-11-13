package com.example.maternacare.repository;

import com.example.maternacare.model.Reminder;
import com.example.maternacare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUser(User user);
    List<Reminder> findByDate(LocalDate date);

    // User-scoped queries for filtering and calendar
    List<Reminder> findByUserAndDate(User user, LocalDate date);
    List<Reminder> findByUserAndDateBetweenOrderByDateAsc(User user, LocalDate start, LocalDate end);
    List<Reminder> findTop5ByUserAndDateGreaterThanEqualOrderByDateAsc(User user, LocalDate start);

    // For cleanup when deleting a user
    void deleteByUser(User user);
}
