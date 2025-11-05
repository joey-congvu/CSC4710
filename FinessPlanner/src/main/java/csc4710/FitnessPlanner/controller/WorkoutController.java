package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.entity.Workout;
import csc4710.FitnessPlanner.service.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout")
@CrossOrigin(origins = "*")
public class WorkoutController {

    private final WorkoutService svc;

    public WorkoutController(WorkoutService svc) {
        this.svc = svc;
    }

    // Create today's workout; duration/notes are optional
    @PostMapping("/create")
    public Workout create(@RequestParam Integer userId,
                          @RequestParam(required = false) Integer durationMin,
                          @RequestParam(required = false) String notes) {
        return svc.createWorkout(userId, durationMin, notes);
    }

    @GetMapping("/today/{userId}")
    public List<Workout> today(@PathVariable Integer userId) {
        return svc.getToday(userId);
    }
}
