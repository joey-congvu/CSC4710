package csc4710.FitnessPlanner.dto;

public class MuscleGroupCountDTO {
    private String muscleGroup;
    private Long sessions;

    public MuscleGroupCountDTO() {}

    public MuscleGroupCountDTO(String muscleGroup, Long sessions) {
        this.muscleGroup = muscleGroup;
        this.sessions = sessions;
    }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }

    public Long getSessions() { return sessions; }
    public void setSessions(Long sessions) { this.sessions = sessions; }
}
