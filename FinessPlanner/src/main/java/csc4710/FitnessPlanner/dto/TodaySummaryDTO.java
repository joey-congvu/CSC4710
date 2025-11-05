// src/main/java/csc4710/FitnessPlanner/dto/TodaySummaryDTO.java
package csc4710.FitnessPlanner.dto;

public class TodaySummaryDTO {
    private double targetCalories, targetProtein, targetCarb, targetFat;
    private double consumedCalories, consumedProtein, consumedCarb, consumedFat;
    private double burnedCalories;
    private double netRemainingCalories;

    public double getTargetCalories() { return targetCalories; }
    public void setTargetCalories(double v) { targetCalories = v; }
    public double getTargetProtein() { return targetProtein; }
    public void setTargetProtein(double v) { targetProtein = v; }
    public double getTargetCarb() { return targetCarb; }
    public void setTargetCarb(double v) { targetCarb = v; }
    public double getTargetFat() { return targetFat; }
    public void setTargetFat(double v) { targetFat = v; }
    public double getConsumedCalories() { return consumedCalories; }
    public void setConsumedCalories(double v) { consumedCalories = v; }
    public double getConsumedProtein() { return consumedProtein; }
    public void setConsumedProtein(double v) { consumedProtein = v; }
    public double getConsumedCarb() { return consumedCarb; }
    public void setConsumedCarb(double v) { consumedCarb = v; }
    public double getConsumedFat() { return consumedFat; }
    public void setConsumedFat(double v) { consumedFat = v; }
    public double getBurnedCalories() { return burnedCalories; }
    public void setBurnedCalories(double v) { burnedCalories = v; }
    public double getNetRemainingCalories() { return netRemainingCalories; }
    public void setNetRemainingCalories(double v) { netRemainingCalories = v; }
}
