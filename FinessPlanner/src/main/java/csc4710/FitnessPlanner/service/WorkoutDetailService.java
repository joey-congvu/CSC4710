package csc4710.FitnessPlanner.service;

import csc4710.FitnessPlanner.entity.WorkoutDetail;
import csc4710.FitnessPlanner.repository.ExerciseRepository;
import csc4710.FitnessPlanner.repository.WorkoutDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkoutDetailService {

    private final WorkoutDetailRepository repo;
    private final ExerciseRepository exerciseRepo;

    public WorkoutDetailService(WorkoutDetailRepository repo, ExerciseRepository exerciseRepo) {
        this.repo = repo;
        this.exerciseRepo = exerciseRepo;
    }
    

    public WorkoutDetail addExerciseToWorkout(Integer workoutId,
                                          Integer exerciseId,
                                          Integer sets,
                                          Integer reps,
                                          Integer secondsPerSet,
                                          Double loadKg) {
    // ✅ Only allow exercises that exist in our database
    exerciseRepo.findById(exerciseId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid exercise ID"));

    WorkoutDetail detail = new WorkoutDetail();
    detail.setWorkoutId(workoutId);
    detail.setExerciseId(exerciseId);
    detail.setSets(sets);
    detail.setReps(reps);
    detail.setSecondsPerSet(secondsPerSet);
    detail.setLoadKg(loadKg);

    return repo.save(detail);
}
}
