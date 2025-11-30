package csc4710.FitnessPlanner.dto;

import java.util.List;

public class WeeklyProgressDTO {
    private String goalType;
    private List<ProgressDayDTO> days;
    private List<MuscleGroupCountDTO> muscleGroups;

    public WeeklyProgressDTO() {}

    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }

    public List<ProgressDayDTO> getDays() { return days; }
    public void setDays(List<ProgressDayDTO> days) { this.days = days; }

    public List<MuscleGroupCountDTO> getMuscleGroups() { return muscleGroups; }
    public void setMuscleGroups(List<MuscleGroupCountDTO> muscleGroups) { this.muscleGroups = muscleGroups; }
}
