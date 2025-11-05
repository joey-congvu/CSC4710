package csc4710.FitnessPlanner.repository;

import csc4710.FitnessPlanner.entity.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MealItemRepository extends JpaRepository<MealItem, Integer> {
    List<MealItem> findByMealId(Integer mealId);
    @Query(value = """
        SELECT f.name AS name,
               COALESCE(SUM(f.calories_per_unit * mi.quantity), 0) AS calories
        FROM meal m
        JOIN meal_item mi ON m.meal_id = mi.meal_id
        JOIN food_item f  ON mi.food_id = f.food_id
        WHERE m.plan_id = :planId
          AND m.log_date = CURDATE()
        GROUP BY f.name
        ORDER BY f.name
        """, nativeQuery = true)
    List<Object[]> findTodayMealsByPlanId(@Param("planId") Integer planId);
    @Modifying
    @Query(value = """
        DELETE mi
        FROM meal_item mi
        JOIN meal m ON m.meal_id = mi.meal_id
        JOIN plan p ON p.plan_id = m.plan_id
        WHERE p.user_id = :userId
          AND m.log_date = CURDATE()
        """, nativeQuery = true)
    int deleteAllTodayMealsByUser(@Param("userId") Integer userId);
}
