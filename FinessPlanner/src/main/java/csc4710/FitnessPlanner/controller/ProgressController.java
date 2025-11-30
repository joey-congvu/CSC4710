package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.dto.WeeklyProgressDTO;
import csc4710.FitnessPlanner.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/weekly/{userId}")
    public ResponseEntity<WeeklyProgressDTO> getWeeklyProgress(@PathVariable Integer userId) {
        WeeklyProgressDTO dto = progressService.getLast7DaysProgress(userId);
        return ResponseEntity.ok(dto);
    }
}
