package csc4710.FitnessPlanner.service;

import csc4710.FitnessPlanner.dto.MuscleGroupCountDTO;
import csc4710.FitnessPlanner.dto.ProgressDayDTO;
import csc4710.FitnessPlanner.dto.WeeklyProgressDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProgressService {

    @PersistenceContext
    private EntityManager em;

    private static final double DEFAULT_WEIGHT_KG = 70.0;

    public WeeklyProgressDTO getLast7DaysProgress(Integer userId) {

        LocalDate end   = LocalDate.now();
        LocalDate start = end.minusDays(6); // 7 days inclusive

        // ---------- 1) Latest plan for this user (targets + goal_type) ----------
        String planSql =
                "SELECT goal_type, daily_calorie_target, protein_g_target, " +
                "       carb_g_target, fat_g_target " +
                "FROM plan " +
                "WHERE user_id = :userId " +
                "ORDER BY start_date DESC, plan_id DESC " +
                "LIMIT 1";

        Query planQ = em.createNativeQuery(planSql);
        planQ.setParameter("userId", userId);

        String  goalType = null;
        Integer tgtKcal  = null;
        Integer tgtP     = null;
        Integer tgtC     = null;
        Integer tgtF     = null;

        @SuppressWarnings("unchecked")
        List<Object[]> planRows = planQ.getResultList();
        if (!planRows.isEmpty()) {
            Object[] row = planRows.get(0);
            goalType = row[0] != null ? row[0].toString() : null;
            tgtKcal  = row[1] != null ? ((Number) row[1]).intValue() : null;
            tgtP     = row[2] != null ? ((Number) row[2]).intValue() : null;
            tgtC     = row[3] != null ? ((Number) row[3]).intValue() : null;
            tgtF     = row[4] != null ? ((Number) row[4]).intValue() : null;
        }

        // ---------- 2) User weight (for calorie burn) ----------
        String weightSql = "SELECT weight_kg FROM users WHERE user_id = :userId";
        Query wQ = em.createNativeQuery(weightSql);
        wQ.setParameter("userId", userId);

        double weightKg = DEFAULT_WEIGHT_KG;
        @SuppressWarnings("unchecked")
        List<Object> wRows = wQ.getResultList();
        if (!wRows.isEmpty() && wRows.get(0) != null) {
            try {
                weightKg = ((Number) wRows.get(0)).doubleValue();
            } catch (Exception ignored) {}
        }

        // ---------- 3) Prepare one ProgressDayDTO per day (default zeros) ----------
        Map<LocalDate, ProgressDayDTO> dayMap = new LinkedHashMap<>();
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            ProgressDayDTO dto = new ProgressDayDTO(d);
            dto.setTargetCalories(tgtKcal);
            dto.setTargetProtein(tgtP);
            dto.setTargetCarb(tgtC);
            dto.setTargetFat(tgtF);
            dayMap.put(d, dto);
        }

        // ---------- 4) Intake per day (kcal + macros) ----------
        String intakeSql =
                "SELECT m.log_date, " +
                "       COALESCE(SUM(fi.calories_per_unit * mi.quantity), 0) AS kcal, " +
                "       COALESCE(SUM(fi.protein_g        * mi.quantity), 0) AS pro, " +
                "       COALESCE(SUM(fi.carbs_g          * mi.quantity), 0) AS carb, " +
                "       COALESCE(SUM(fi.fat_g            * mi.quantity), 0) AS fat " +
                "FROM meal m " +
                "JOIN plan p ON m.plan_id = p.plan_id " +
                "LEFT JOIN meal_item mi ON mi.meal_id = m.meal_id " +
                "LEFT JOIN food_item fi ON fi.food_id = mi.food_id " +
                "WHERE p.user_id = :userId " +
                "  AND m.log_date BETWEEN :start AND :end " +
                "GROUP BY m.log_date";

        Query intakeQ = em.createNativeQuery(intakeSql);
        intakeQ.setParameter("userId", userId);
        intakeQ.setParameter("start", Date.valueOf(start));
        intakeQ.setParameter("end",   Date.valueOf(end));

        @SuppressWarnings("unchecked")
        List<Object[]> intakeRows = intakeQ.getResultList();
        for (Object[] row : intakeRows) {
            LocalDate d = ((Date) row[0]).toLocalDate();
            ProgressDayDTO dto = dayMap.get(d);
            if (dto == null) continue;

            dto.setConsumedCalories(toDouble(row[1]));
            dto.setConsumedProtein(toDouble(row[2]));
            dto.setConsumedCarb(toDouble(row[3]));
            dto.setConsumedFat(toDouble(row[4]));
        }

        // ---------- 5) Burned calories per day (MET * duration) ----------
        String workoutSql =
                "SELECT w.log_date, " +
                "       COALESCE(SUM(e.met_value * w.duration_min), 0) AS met_minutes " +
                "FROM workout w " +
                "JOIN plan p ON w.plan_id = p.plan_id " +
                "LEFT JOIN workout_detail wd ON wd.workout_id = w.workout_id " +
                "LEFT JOIN exercise e ON e.exercise_id = wd.exercise_id " +
                "WHERE p.user_id = :userId " +
                "  AND w.log_date BETWEEN :start AND :end " +
                "GROUP BY w.log_date";

        Query workoutQ = em.createNativeQuery(workoutSql);
        workoutQ.setParameter("userId", userId);
        workoutQ.setParameter("start", Date.valueOf(start));
        workoutQ.setParameter("end",   Date.valueOf(end));

        @SuppressWarnings("unchecked")
        List<Object[]> workoutRows = workoutQ.getResultList();
        for (Object[] row : workoutRows) {
            LocalDate d = ((Date) row[0]).toLocalDate();
            ProgressDayDTO dto = dayMap.get(d);
            if (dto == null) continue;

            double metMinutes = toDouble(row[1]);
            // kcal = MET * 3.5 * weight_kg / 200 * minutes
            double burned = metMinutes * 3.5 * weightKg / 200.0;
            dto.setBurnedCalories(burned);
        }

        // ---------- 6) Workout frequency by muscle group ----------
        String mgSql =
                "SELECT COALESCE(e.muscle_group, 'Other') AS mg, " +
                "       COUNT(DISTINCT w.workout_id) AS sessions " +
                "FROM workout w " +
                "JOIN plan p ON w.plan_id = p.plan_id " +
                "LEFT JOIN workout_detail wd ON wd.workout_id = w.workout_id " +
                "LEFT JOIN exercise e ON e.exercise_id = wd.exercise_id " +
                "WHERE p.user_id = :userId " +
                "  AND w.log_date BETWEEN :start AND :end " +
                "GROUP BY COALESCE(e.muscle_group, 'Other') " +
                "ORDER BY sessions DESC";

        Query mgQ = em.createNativeQuery(mgSql);
        mgQ.setParameter("userId", userId);
        mgQ.setParameter("start", Date.valueOf(start));
        mgQ.setParameter("end",   Date.valueOf(end));

        @SuppressWarnings("unchecked")
        List<Object[]> mgRows = mgQ.getResultList();
        List<MuscleGroupCountDTO> muscleGroups = new ArrayList<>();
        for (Object[] row : mgRows) {
            String mg = row[0] != null ? row[0].toString() : "Other";
            Long sessions = row[1] == null ? 0L : ((Number) row[1]).longValue();
            muscleGroups.add(new MuscleGroupCountDTO(mg, sessions));
        }

        // ---------- 7) Build result ----------
        WeeklyProgressDTO result = new WeeklyProgressDTO();
        result.setGoalType(goalType);
        result.setDays(new ArrayList<>(dayMap.values()));
        result.setMuscleGroups(muscleGroups);
        return result;
    }

    private double toDouble(Object o) {
        if (o == null) return 0.0;
        return ((Number) o).doubleValue();
    }
}
