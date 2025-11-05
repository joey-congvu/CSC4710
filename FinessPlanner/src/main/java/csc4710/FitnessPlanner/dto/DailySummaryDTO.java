package csc4710.FitnessPlanner.dto;

public class DailySummaryDTO {

    private double targetCalories;
    private double consumedCalories;
    private double remainingCalories;

    private double targetProtein;
    private double consumedProtein;
    private double remainingProtein;

    private double targetCarb;
    private double consumedCarb;
    private double remainingCarb;

    private double targetFat;
    private double consumedFat;
    private double remainingFat;

    // --- Getters and Setters ---
    public double getTargetCalories() { return targetCalories; }
    public void setTargetCalories(double targetCalories) { this.targetCalories = targetCalories; }

    public double getConsumedCalories() { return consumedCalories; }
    public void setConsumedCalories(double consumedCalories) { this.consumedCalories = consumedCalories; }

    public double getRemainingCalories() { return remainingCalories; }
    public void setRemainingCalories(double remainingCalories) { this.remainingCalories = remainingCalories; }

    public double getTargetProtein() { return targetProtein; }
    public void setTargetProtein(double targetProtein) { this.targetProtein = targetProtein; }

    public double getConsumedProtein() { return consumedProtein; }
    public void setConsumedProtein(double consumedProtein) { this.consumedProtein = consumedProtein; }

    public double getRemainingProtein() { return remainingProtein; }
    public void setRemainingProtein(double remainingProtein) { this.remainingProtein = remainingProtein; }

    public double getTargetCarb() { return targetCarb; }
    public void setTargetCarb(double targetCarb) { this.targetCarb = targetCarb; }

    public double getConsumedCarb() { return consumedCarb; }
    public void setConsumedCarb(double consumedCarb) { this.consumedCarb = consumedCarb; }

    public double getRemainingCarb() { return remainingCarb; }
    public void setRemainingCarb(double remainingCarb) { this.remainingCarb = remainingCarb; }

    public double getTargetFat() { return targetFat; }
    public void setTargetFat(double targetFat) { this.targetFat = targetFat; }

    public double getConsumedFat() { return consumedFat; }
    public void setConsumedFat(double consumedFat) { this.consumedFat = consumedFat; }

    public double getRemainingFat() { return remainingFat; }
    public void setRemainingFat(double remainingFat) { this.remainingFat = remainingFat; }
}
