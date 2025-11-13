package com.example.maternacare.service;

import com.example.maternacare.model.Milestone;
import com.example.maternacare.repository.MilestoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public int calculateCurrentWeek(LocalDate dueDate) {
        if (dueDate == null) return 0;
        LocalDate conceptionDateEstimate = dueDate.minusWeeks(40);
        long weeks = ChronoUnit.WEEKS.between(conceptionDateEstimate, LocalDate.now());
        return (int) Math.max(0, Math.min(40, weeks));
    }

    public List<Milestone> getMilestonesForWeek(int week) {
        return milestoneRepository.findByWeekNumber(week);
    }
}
