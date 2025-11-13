package com.minicloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.minicloud.model.User;
import com.minicloud.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createUser(User user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use: " + email);
            
        }
        try {
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        try {
            userRepository.deleteById(id);
            return "User deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String existUser(String username, String email) {
        boolean existsByUsername = userExistsByUsername(username);
        boolean existsByEmail = userExistsByEmail(email);
        return "Exists by username: " + existsByUsername + ", Exists by email: " + existsByEmail;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
