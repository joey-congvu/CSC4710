// src/main/java/csc4710/FitnessPlanner/service/WorkoutService.java
package csc4710.FitnessPlanner.service;

import csc4710.FitnessPlanner.entity.Plan;
import csc4710.FitnessPlanner.entity.Workout;
import csc4710.FitnessPlanner.repository.PlanRepository;
import csc4710.FitnessPlanner.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepo;
    private final PlanRepository planRepo;

    public WorkoutService(WorkoutRepository workoutRepo, PlanRepository planRepo) {
        this.workoutRepo = workoutRepo;
        this.planRepo = planRepo;
    }

   @Transactional
    public Workout createWorkout(Integer userId, Integer durationMin, String notes) {
        Integer planId = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .map(Plan::getPlanId)
                .orElseThrow(() -> new IllegalStateException("User has no plan yet"));
        Workout w = new Workout();
        w.setPlanId(planId);
        w.setLogDate(LocalDate.now());
        w.setDurationMin(durationMin);   // may be null
        w.setNotes(notes);               // may be null/empty
        return workoutRepo.save(w);
    }


    public List<Workout> getToday(Integer userId) {
        Integer planId = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .map(Plan::getPlanId).orElseThrow(() -> new IllegalStateException("User has no plan yet"));
        return workoutRepo.findByPlanIdAndLogDate(planId, LocalDate.now());
    }
}
