package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.entity.MealItem;
import csc4710.FitnessPlanner.repository.MealItemRepository;
import csc4710.FitnessPlanner.repository.PlanRepository;
import csc4710.FitnessPlanner.service.MealItemService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/meal_item")
@CrossOrigin(origins = "*")
public class MealItemController {

    private final MealItemService mealItemService;
    private final PlanRepository planRepo;
    private final MealItemRepository mealItemRepo;

    public MealItemController(MealItemService mealItemService,
                              PlanRepository planRepo,
                              MealItemRepository mealItemRepo) {
        this.mealItemService = mealItemService;
        this.planRepo = planRepo;
        this.mealItemRepo = mealItemRepo;
    }

    // ✅ Add food to a meal
    @PostMapping("/add")
    public MealItem addFoodToMeal(@RequestParam Integer mealId,
                                  @RequestParam Integer foodId,
                                  @RequestParam Double quantity) {
        return mealItemService.addFoodToMeal(mealId, foodId, quantity);
    }

    // ✅ Get all foods in a meal
    @GetMapping("/byMeal/{mealId}")
    public List<MealItem> getItemsByMeal(@PathVariable Integer mealId) {
        return mealItemService.getItemsByMeal(mealId);
    }

    // ✅ Get all foods eaten today (food name + calories)
    @GetMapping("/today/{userId}")
    public List<Map<String, Object>> getTodayMeals(@PathVariable Integer userId) {
        Integer planId = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .map(p -> p.getPlanId())
                .orElseThrow(() -> new IllegalStateException("User has no plan yet"));

        List<Object[]> rows = mealItemRepo.findTodayMealsByPlanId(planId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] r : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", r[0]);
            map.put("calories", r[1]);
            result.add(map);
        }

        return result;
    }
    @DeleteMapping("/today/{userId}")
    public Map<String, Object> deleteAllTodayMeals(@PathVariable Integer userId) {
        int count = mealItemService.deleteAllTodayMealsByUser(userId);
        return Map.of("deleted", count, "message", "Deleted all meals logged today.");
    }
}
