package csc4710.FitnessPlanner.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Integer exerciseId;

    @Column(name = "name")
    private String name;

    @Column(name = "met_value")
    private Double metValue;

    // --- getters/setters ---
    public Integer getExerciseId() { return exerciseId; }
    public void setExerciseId(Integer exerciseId) { this.exerciseId = exerciseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getMetValue() { return metValue; }
    public void setMetValue(Double caloriesPerMin) { this.metValue = caloriesPerMin; }
}
