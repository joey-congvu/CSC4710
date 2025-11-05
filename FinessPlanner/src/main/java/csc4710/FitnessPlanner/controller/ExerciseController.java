package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.entity.Exercise;
import csc4710.FitnessPlanner.repository.ExerciseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@CrossOrigin(origins = "*")
public class ExerciseController {

    private final ExerciseRepository exerciseRepo;

    public ExerciseController(ExerciseRepository exerciseRepo) {
        this.exerciseRepo = exerciseRepo;
    }

    // ✅ Get all predefined exercises (user can only choose from these)
    @GetMapping("/all")
    public List<Exercise> getAllExercises() {
        return exerciseRepo.findAll();
    }
}
