package csc4710.FitnessPlanner.repository;

import csc4710.FitnessPlanner.entity.WorkoutDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface WorkoutDetailRepository extends JpaRepository<WorkoutDetail, Integer> {

    List<WorkoutDetail> findByWorkoutId(Integer workoutId);

    @Query(value = """
        /* kcal = MET * 3.5 * weight_kg / 200 * minutes
           minutes here = sets * seconds_per_set / 60.0 (per exercise) */
        SELECT e.name AS name,
               COALESCE(SUM(
                   e.met_value * 3.5 * u.weight_kg / 200 *
                   (COALESCE(wd.sets, 1) * COALESCE(wd.seconds_per_set, 0) / 60.0)
               ), 0) AS calories
        FROM workout w
        JOIN workout_detail wd ON w.workout_id = wd.workout_id
        JOIN exercise e        ON wd.exercise_id = e.exercise_id
        JOIN plan p            ON w.plan_id = p.plan_id
        JOIN users u           ON p.user_id  = u.user_id
        WHERE w.plan_id = :planId
          AND w.log_date = CURDATE()
        GROUP BY e.name
        ORDER BY e.name
        """, nativeQuery = true)
    List<Object[]> findTodayWorkoutsByPlanId(@Param("planId") Integer planId);
}

