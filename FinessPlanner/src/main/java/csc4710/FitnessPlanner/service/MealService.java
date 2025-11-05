package csc4710.FitnessPlanner.service;

import csc4710.FitnessPlanner.dto.DailySummaryDTO;
import csc4710.FitnessPlanner.entity.Meal;
import csc4710.FitnessPlanner.entity.Plan;
import csc4710.FitnessPlanner.repository.MealRepository;
import csc4710.FitnessPlanner.repository.PlanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepo;
    private final PlanRepository planRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public MealService(MealRepository mealRepo, PlanRepository planRepo) {
        this.mealRepo = mealRepo;
        this.planRepo = planRepo;
    }

    // ==============================
    // 1. Create new meal for today
    // ==============================
    @Transactional
    public Meal createMeal(Integer userId, String mealType) {
        Integer latestPlanId = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .map(p -> p.getPlanId())
                .orElseThrow(() -> new IllegalStateException("User has no plan yet"));

        Meal meal = new Meal();
        meal.setPlanId(latestPlanId);
        meal.setMealType(mealType);
        meal.setLogDate(LocalDate.now());

        return mealRepo.save(meal);
    }

    // ==============================
    // 2. Get today’s meals
    // ==============================
    public List<Meal> getMealsForToday(Integer userId) {
        Integer latestPlanId = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .map(p -> p.getPlanId())
                .orElseThrow(() -> new IllegalStateException("User has no plan yet"));

        return mealRepo.findByPlanIdAndLogDate(latestPlanId, LocalDate.now());
    }

    // ==============================
    // 3. Get daily nutrition summary
    // ==============================
    @Transactional
    public DailySummaryDTO getDailySummary(Integer userId) {
        // Step 1: Get latest plan
        Plan plan = planRepo.findTopByUserIdOrderByPlanIdDesc(userId)
                .orElseThrow(() -> new IllegalStateException("User has no plan yet"));

        Integer planId = plan.getPlanId();

        // Step 2: Run native SQL query to compute totals
        String sql = """
            SELECT 
                COALESCE(SUM(f.calories_per_unit * mi.quantity), 0),
                COALESCE(SUM(f.protein_g * mi.quantity), 0),
                COALESCE(SUM(f.carbs_g * mi.quantity), 0),
                COALESCE(SUM(f.fat_g * mi.quantity), 0)
            FROM meal m
            JOIN meal_item mi ON m.meal_id = mi.meal_id
            JOIN food_item f ON mi.food_id = f.food_id
            WHERE m.plan_id = :planId AND m.log_date = CURDATE()
        """;

        Object[] result = (Object[]) entityManager.createNativeQuery(sql)
                .setParameter("planId", planId)
                .getSingleResult();

        double consumedCalories = ((Number) result[0]).doubleValue();
        double consumedProtein  = ((Number) result[1]).doubleValue();
        double consumedCarb     = ((Number) result[2]).doubleValue();
        double consumedFat      = ((Number) result[3]).doubleValue();

        // Step 3: Build DTO
        DailySummaryDTO dto = new DailySummaryDTO();

        dto.setTargetCalories(plan.getDailyCalorieTarget());
        dto.setTargetProtein(plan.getProteinGTarget());
        dto.setTargetCarb(plan.getCarbGTarget());
        dto.setTargetFat(plan.getFatGTarget());

        dto.setConsumedCalories(consumedCalories);
        dto.setConsumedProtein(consumedProtein);
        dto.setConsumedCarb(consumedCarb);
        dto.setConsumedFat(consumedFat);

        dto.setRemainingCalories(Math.max(0, plan.getDailyCalorieTarget() - consumedCalories));
        dto.setRemainingProtein(Math.max(0, plan.getProteinGTarget() - consumedProtein));
        dto.setRemainingCarb(Math.max(0, plan.getCarbGTarget() - consumedCarb));
        dto.setRemainingFat(Math.max(0, plan.getFatGTarget() - consumedFat));

        return dto;
    }
}
