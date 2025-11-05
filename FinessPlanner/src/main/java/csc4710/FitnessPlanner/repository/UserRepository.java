package csc4710.FitnessPlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import csc4710.FitnessPlanner.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
