package csc4710.FitnessPlanner.repository;

import csc4710.FitnessPlanner.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    // For “today” lookups by the user’s latest plan
    List<Workout> findByPlanIdAndLogDate(Integer planId, LocalDate logDate);

    // Optional: fetch the most recently created workout today (handy after create)
    Optional<Workout> findTopByPlanIdAndLogDateOrderByWorkoutIdDesc(Integer planId, LocalDate logDate);
}
