package csc4710.FitnessPlanner.service;

import csc4710.FitnessPlanner.entity.MealItem;
import csc4710.FitnessPlanner.repository.MealItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MealItemService {

    private final MealItemRepository mealItemRepo;

    public MealItemService(MealItemRepository mealItemRepo) {
        this.mealItemRepo = mealItemRepo;
    }

    public MealItem addFoodToMeal(Integer mealId, Integer foodId, Double quantity) {
        MealItem mi = new MealItem();
        mi.setMealId(mealId);
        mi.setFoodId(foodId);
        mi.setQuantity(quantity);
        return mealItemRepo.save(mi);
    }

    public List<MealItem> getItemsByMeal(Integer mealId) {
        return mealItemRepo.findByMealId(mealId);
    }  
    @Transactional
    public int deleteAllTodayMealsByUser(Integer userId) {
        return mealItemRepo.deleteAllTodayMealsByUser(userId);
    }
}
   

