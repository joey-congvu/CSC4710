package csc4710.FitnessPlanner.dto;

import java.time.LocalDate;

public class ProgressDayDTO {
    private LocalDate date;

    private Integer targetCalories;
    private Integer targetProtein;
    private Integer targetCarb;
    private Integer targetFat;

    private Double consumedCalories;
    private Double burnedCalories;

    private Double consumedProtein;
    private Double consumedCarb;
    private Double consumedFat;

    public ProgressDayDTO() {}

    public ProgressDayDTO(LocalDate date) {
        this.date = date;
        this.consumedCalories = 0.0;
        this.burnedCalories = 0.0;
        this.consumedProtein = 0.0;
        this.consumedCarb = 0.0;
        this.consumedFat = 0.0;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Integer getTargetCalories() { return targetCalories; }
    public void setTargetCalories(Integer targetCalories) { this.targetCalories = targetCalories; }

    public Integer getTargetProtein() { return targetProtein; }
    public void setTargetProtein(Integer targetProtein) { this.targetProtein = targetProtein; }

    public Integer getTargetCarb() { return targetCarb; }
    public void setTargetCarb(Integer targetCarb) { this.targetCarb = targetCarb; }

    public Integer getTargetFat() { return targetFat; }
    public void setTargetFat(Integer targetFat) { this.targetFat = targetFat; }

    public Double getConsumedCalories() { return consumedCalories; }
    public void setConsumedCalories(Double consumedCalories) { this.consumedCalories = consumedCalories; }

    public Double getBurnedCalories() { return burnedCalories; }
    public void setBurnedCalories(Double burnedCalories) { this.burnedCalories = burnedCalories; }

    public Double getConsumedProtein() { return consumedProtein; }
    public void setConsumedProtein(Double consumedProtein) { this.consumedProtein = consumedProtein; }

    public Double getConsumedCarb() { return consumedCarb; }
    public void setConsumedCarb(Double consumedCarb) { this.consumedCarb = consumedCarb; }

    public Double getConsumedFat() { return consumedFat; }
    public void setConsumedFat(Double consumedFat) { this.consumedFat = consumedFat; }
}
