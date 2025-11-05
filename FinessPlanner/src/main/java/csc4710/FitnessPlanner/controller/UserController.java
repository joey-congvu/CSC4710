package csc4710.FitnessPlanner.controller;

import csc4710.FitnessPlanner.entity.User;
import csc4710.FitnessPlanner.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    @PutMapping("/{userId}")
    public User updateHW(@PathVariable Integer userId, @RequestBody User updatedData) {
        Optional<User> existingOpt = userRepo.findById(userId);
        if (existingOpt.isEmpty()) throw new IllegalArgumentException("User not found");

        User existing = existingOpt.get();
        if (updatedData.getHeightCm() != null) existing.setHeightCm(updatedData.getHeightCm());
        if (updatedData.getWeightKg() != null) existing.setWeightKg(updatedData.getWeightKg());
        return userRepo.save(existing);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        Optional<User> user = userRepo.findById(id);
        return user.<ResponseEntity<?>>map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
