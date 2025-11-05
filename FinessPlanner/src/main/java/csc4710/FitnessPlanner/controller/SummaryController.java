// src/main/java/csc4710/FitnessPlanner/controller/SummaryController.java
package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.dto.TodaySummaryDTO;
import csc4710.FitnessPlanner.service.SummaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/summary")
@CrossOrigin(origins = "*")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    // GET /api/summary/today/{userId}
    @GetMapping("/today/{userId}")
    public TodaySummaryDTO today(@PathVariable Integer userId) {
        try {
            return summaryService.today(userId);
        } catch (IllegalStateException e) { // e.g., "User has no plan yet"
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to build summary", e);
        }
    }

    // Optional quick check: GET /api/summary/ping -> "ok"
    @GetMapping("/ping")
    public String ping() { return "ok"; }
}
