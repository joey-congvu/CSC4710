package csc4710.FitnessPlanner.repository;

import csc4710.FitnessPlanner.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> findByPlanIdAndLogDate(Integer planId, LocalDate logDate);
}
