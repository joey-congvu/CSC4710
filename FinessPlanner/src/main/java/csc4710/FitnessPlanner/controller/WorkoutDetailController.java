package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.entity.WorkoutDetail;
import csc4710.FitnessPlanner.repository.PlanRepository;
import csc4710.FitnessPlanner.repository.WorkoutDetailRepository;
import csc4710.FitnessPlanner.service.WorkoutDetailService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/workout_detail")
@CrossOrigin(origins = "*")
public class WorkoutDetailController {

    private final WorkoutDetailService workoutDetailService;
    private final PlanRepository planRepo;
    private final WorkoutDetailRepository workoutDetailRepo;

    public WorkoutDetailController(WorkoutDetailService workoutDetailService,
                                   PlanRepository planRepo,
                                   WorkoutDetailRepository workoutDetailRepo) {
        this.workoutDetailService = workoutDetailService;
        this.planRepo = planRepo;
        this.workoutDetailRepo = workoutDetailRepo;
    }

    // ✅ Add exercise with sets/reps/seconds_per_set/load_kg
    @PostMapping("/add")
    public WorkoutDetail addExercise(@RequestParam Integer workoutId,
                                     @RequestParam Integer exerciseId,
                                     @RequestParam(required = false) Integer sets,
                                     @RequestParam(required = false) Integer reps,
                                     @RequestParam(required = false, name = "secondsPerSet") Integer secondsPerSet,
                                     @RequestParam(required = false, name = "loadKg") Double loadKg) {
        return workoutDetailService.addExerciseToWorkout(
                workoutId, exerciseId, sets, reps, secondsPerSet, loadKg
        );
    }

    // ✅ Get all workouts done today (exercise name + calories burned)
    @GetMapping("/today/{userId}")
    public List<Map<String, Object>> getTodayWorkouts(@PathVariable Integer userId) {
        Integer planId = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .map(p -> p.getPlanId())
                .orElseThrow(() -> new IllegalStateException("User has no plan yet"));

        List<Object[]> rows = workoutDetailRepo.findTodayWorkoutsByPlanId(planId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", r[0]);
            map.put("calories", r[1]);
            result.add(map);
        }

        return result;
    }
}
