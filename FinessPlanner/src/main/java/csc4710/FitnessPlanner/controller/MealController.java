package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.dto.DailySummaryDTO;
import csc4710.FitnessPlanner.entity.Meal;
import csc4710.FitnessPlanner.service.MealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal")
@CrossOrigin(origins = "*")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    // 1. Create a meal (Breakfast, Lunch, etc.)
    @PostMapping("/create")
    public Meal createMeal(@RequestParam Integer userId,
                           @RequestParam String mealType) {
        return mealService.createMeal(userId, mealType);
    }

    // 2. Get today's meals for a user
    @GetMapping("/today/{userId}")
    public List<Meal> getMealsForToday(@PathVariable Integer userId) {
        return mealService.getMealsForToday(userId);
    }

    // 3. Get today’s nutrition summary (target vs consumed vs remaining)
    @GetMapping("/summary/{userId}")
    public DailySummaryDTO getDailySummary(@PathVariable Integer userId) {
        return mealService.getDailySummary(userId);
    }
}
