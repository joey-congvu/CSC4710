package csc4710.FitnessPlanner.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Integer workoutId;

    @Column(name = "plan_id", nullable = false)
    private Integer planId;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "duration_min")
    private Integer durationMin;   // optional: total duration of this workout

    @Column(name = "notes")
    private String notes;

    // --- getters & setters ---
    public Integer getWorkoutId() { return workoutId; }
    public void setWorkoutId(Integer workoutId) { this.workoutId = workoutId; }

    public Integer getPlanId() { return planId; }
    public void setPlanId(Integer planId) { this.planId = planId; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public Integer getDurationMin() { return durationMin; }
    public void setDurationMin(Integer durationMin) { this.durationMin = durationMin; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
