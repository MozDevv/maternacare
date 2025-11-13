package com.example.maternacare.repository;

import com.example.maternacare.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findByWeekNumber(Integer weekNumber);
}
