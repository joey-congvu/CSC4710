package csc4710.FitnessPlanner.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_item")
public class MealItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_item_id")
    private Integer mealItemId;

    @Column(name = "meal_id")
    private Integer mealId;

    @Column(name = "food_id")
    private Integer foodId;

    @Column(name = "quantity")
    private Double quantity;

    // --- getters & setters ---
    public Integer getMealItemId() { return mealItemId; }
    public void setMealItemId(Integer mealItemId) { this.mealItemId = mealItemId; }

    public Integer getMealId() { return mealId; }
    public void setMealId(Integer mealId) { this.mealId = mealId; }

    public Integer getFoodId() { return foodId; }
    public void setFoodId(Integer foodId) { this.foodId = foodId; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
}
