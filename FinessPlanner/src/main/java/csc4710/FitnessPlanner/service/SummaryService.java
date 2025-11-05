// src/main/java/csc4710/FitnessPlanner/service/SummaryService.java
package csc4710.FitnessPlanner.service;

import csc4710.FitnessPlanner.dto.TodaySummaryDTO;
import csc4710.FitnessPlanner.entity.Plan;
import csc4710.FitnessPlanner.repository.PlanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SummaryService {
    private final PlanRepository planRepo;
    @PersistenceContext private EntityManager em;

    public SummaryService(PlanRepository planRepo) { this.planRepo = planRepo; }

    @Transactional
    public TodaySummaryDTO today(Integer userId) {
        Plan plan = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .orElseThrow(() -> new IllegalStateException("User has no plan yet"));
        Integer planId = plan.getPlanId();
        LocalDate today = LocalDate.now();

        // ----- Consumed (meals) -----
        Object[] mealAgg = (Object[]) em.createNativeQuery("""
            SELECT
              COALESCE(SUM(f.calories_per_unit * mi.quantity), 0),
              COALESCE(SUM(f.protein_g        * mi.quantity), 0),
              COALESCE(SUM(f.carbs_g          * mi.quantity), 0),
              COALESCE(SUM(f.fat_g            * mi.quantity), 0)
            FROM meal m
            JOIN meal_item mi ON m.meal_id = mi.meal_id
            JOIN food_item f  ON mi.food_id = f.food_id
            WHERE m.plan_id = :pid AND m.log_date = :today
        """).setParameter("pid", planId)
          .setParameter("today", today)
          .getSingleResult();

        double consumedCal = ((Number) mealAgg[0]).doubleValue();
        double consumedPro = ((Number) mealAgg[1]).doubleValue();
        double consumedCar = ((Number) mealAgg[2]).doubleValue();
        double consumedFat = ((Number) mealAgg[3]).doubleValue();

        // ----- Burned (workouts via MET, sets/reps/seconds_per_set) -----
        Number burnedN = (Number) em.createNativeQuery("""
            SELECT COALESCE(SUM(
                e.met_value * 3.5 * u.weight_kg / 200.0
                * ((wd.sets * wd.reps * wd.seconds_per_set) / 60.0)
            ), 0)
            FROM workout w
            JOIN workout_detail wd ON w.workout_id = wd.workout_id
            JOIN exercise e        ON wd.exercise_id = e.exercise_id
            JOIN plan p            ON w.plan_id = p.plan_id
            JOIN users u           ON p.user_id = u.user_id
            WHERE w.plan_id = :pid AND w.log_date = :today
        """).setParameter("pid", planId)
          .setParameter("today", today)
          .getSingleResult();

        double burnedCal = burnedN.doubleValue();

        TodaySummaryDTO dto = new TodaySummaryDTO();
        dto.setTargetCalories(plan.getDailyCalorieTarget());
        dto.setTargetProtein(plan.getProteinGTarget());
        dto.setTargetCarb(plan.getCarbGTarget());
        dto.setTargetFat(plan.getFatGTarget());

        dto.setConsumedCalories(consumedCal);
        dto.setConsumedProtein(consumedPro);
        dto.setConsumedCarb(consumedCar);
        dto.setConsumedFat(consumedFat);

        dto.setBurnedCalories(burnedCal);
        dto.setNetRemainingCalories(Math.max(0.0,
                plan.getDailyCalorieTarget() - (consumedCal - burnedCal)));

        return dto;
    }
}
