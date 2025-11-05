package csc4710.FitnessPlanner.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "workout_detail")
public class WorkoutDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_detail_id")
    private Integer workoutDetailId;   // surrogate PK for JPA (recommended)

    @Column(name = "workout_id", nullable = false)
    private Integer workoutId;

    @Column(name = "exercise_id", nullable = false)
    private Integer exerciseId;

    @Column(name = "sets")
    private Integer sets;

    @Column(name = "reps")
    private Integer reps;

    @Column(name = "seconds_per_set")
    private Integer secondsPerSet;

    @Column(name = "load_kg")
    private Double loadKg;

    // --- getters & setters ---
    public Integer getWorkoutDetailId() { return workoutDetailId; }
    public void setWorkoutDetailId(Integer workoutDetailId) { this.workoutDetailId = workoutDetailId; }

    public Integer getWorkoutId() { return workoutId; }
    public void setWorkoutId(Integer workoutId) { this.workoutId = workoutId; }

    public Integer getExerciseId() { return exerciseId; }
    public void setExerciseId(Integer exerciseId) { this.exerciseId = exerciseId; }

    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }

    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }

    public Integer getSecondsPerSet() { return secondsPerSet; }
    public void setSecondsPerSet(Integer secondsPerSet) { this.secondsPerSet = secondsPerSet; }

    public Double getLoadKg() { return loadKg; }
    public void setLoadKg(Double loadKg) { this.loadKg = loadKg; }
}
