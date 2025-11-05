package csc4710.FitnessPlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import csc4710.FitnessPlanner.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
}
