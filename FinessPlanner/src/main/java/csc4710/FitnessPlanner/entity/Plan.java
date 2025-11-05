package csc4710.FitnessPlanner.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "goal_type")
    private String goalType;

    @Column(name = "daily_calorie_target")
    private Integer dailyCalorieTarget;

    @Column(name = "protein_g_target")
    private Integer proteinGTarget;

    @Column(name = "carb_g_target")
    private Integer carbGTarget;

    @Column(name = "fat_g_target")
    private Integer fatGTarget;

    // Getters and Setters
    public Integer getPlanId() { return planId; }
    public void setPlanId(Integer planId) { this.planId = planId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }

    public Integer getDailyCalorieTarget() { return dailyCalorieTarget; }
    public void setDailyCalorieTarget(Integer dailyCalorieTarget) { this.dailyCalorieTarget = dailyCalorieTarget; }

    public Integer getProteinGTarget() { return proteinGTarget; }
    public void setProteinGTarget(Integer proteinGTarget) { this.proteinGTarget = proteinGTarget; }

    public Integer getCarbGTarget() { return carbGTarget; }
    public void setCarbGTarget(Integer carbGTarget) { this.carbGTarget = carbGTarget; }

    public Integer getFatGTarget() { return fatGTarget; }
    public void setFatGTarget(Integer fatGTarget) { this.fatGTarget = fatGTarget; }
}
