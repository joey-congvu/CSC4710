package csc4710.FitnessPlanner.repository;

import csc4710.FitnessPlanner.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
    List<Plan> findByUserId(Integer userId);

    // Most recent plan for a user by descending planId
    Optional<Plan> findTopByUserIdOrderByPlanIdDesc(Integer userId);
    // (alias: Optional<Plan> findFirstByUserIdOrderByPlanIdDesc(Integer userId);)
}
