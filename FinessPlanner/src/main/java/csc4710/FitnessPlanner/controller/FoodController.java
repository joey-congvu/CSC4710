package csc4710.FitnessPlanner.controller;

import org.springframework.web.bind.annotation.*;
import csc4710.FitnessPlanner.repository.FoodItemRepository;
import csc4710.FitnessPlanner.entity.FoodItem;
import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "*")
public class FoodController {

    private final FoodItemRepository foodRepo;

    public FoodController(FoodItemRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

    @GetMapping("/all")
    public List<FoodItem> getAllFoods() {
        return foodRepo.findAll();
    }
}
