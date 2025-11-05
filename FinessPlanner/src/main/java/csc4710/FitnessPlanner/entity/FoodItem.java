package csc4710.FitnessPlanner.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "food_item")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Integer foodId;

    // DB column is `name`
    @Column(name = "name")
    private String foodName;

    // DB column is `calories`
    @Column(name = "calories_per_unit")
    private Double caloriesPerUnit;

    @Column(name = "protein_g")
    private Double proteinG;

    // DB column is `carbs_g` (with "s")
    @Column(name = "carbs_g")
    private Double carbG;

    @Column(name = "fat_g")
    private Double fatG;

    // --- getters & setters ---
    public Integer getFoodId() { return foodId; }
    public void setFoodId(Integer foodId) { this.foodId = foodId; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public Double getCaloriesPerUnit() { return caloriesPerUnit; }
    public void setCaloriesPerUnit(Double caloriesPerUnit) { this.caloriesPerUnit = caloriesPerUnit; }

    public Double getProteinG() { return proteinG; }
    public void setProteinG(Double proteinG) { this.proteinG = proteinG; }

    public Double getCarbG() { return carbG; }
    public void setCarbG(Double carbG) { this.carbG = carbG; }

    public Double getFatG() { return fatG; }
    public void setFatG(Double fatG) { this.fatG = fatG; }
}
