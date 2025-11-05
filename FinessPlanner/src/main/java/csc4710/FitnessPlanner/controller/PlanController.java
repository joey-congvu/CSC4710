package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.entity.Plan;
import csc4710.FitnessPlanner.repository.PlanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    private final PlanRepository planRepo;

    public PlanController(PlanRepository planRepo) {
        this.planRepo = planRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPlan(@RequestBody Plan plan) {
        planRepo.save(plan);
        return ResponseEntity.ok("Plan created successfully!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Plan>> getUserPlans(@PathVariable Integer userId) {
        return ResponseEntity.ok(planRepo.findByUserId(userId));
    }
}
