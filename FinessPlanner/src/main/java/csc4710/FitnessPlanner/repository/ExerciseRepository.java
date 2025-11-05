package csc4710.FitnessPlanner.repository;

import csc4710.FitnessPlanner.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
}
