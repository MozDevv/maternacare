package com.example.maternacare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "milestones")
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer weekNumber;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(length = 1000)
    private String tip;

    public Milestone() {}

    public Milestone(Long id, Integer weekNumber, String description, String tip) {
        this.id = id;
        this.weekNumber = weekNumber;
        this.description = description;
        this.tip = tip;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getWeekNumber() { return weekNumber; }
    public void setWeekNumber(Integer weekNumber) { this.weekNumber = weekNumber; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTip() { return tip; }
    public void setTip(String tip) { this.tip = tip; }
}
