package csc4710.FitnessPlanner.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import csc4710.FitnessPlanner.entity.User;
import csc4710.FitnessPlanner.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        return userRepo.findByEmail(loginRequest.getEmail())
                .filter(u -> u.getPassword().equals(loginRequest.getPassword()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(u))
                .orElseGet(() -> ResponseEntity.status(401).body("Invalid email or password"));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        // check if email already exists
        if (userRepo.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        userRepo.save(newUser);
        return ResponseEntity.ok("User created successfully");
    }
}
