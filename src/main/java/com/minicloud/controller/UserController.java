package com.minicloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minicloud.dto.UserDTO;
import com.minicloud.mapper.UserMapper;
import com.minicloud.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private static UserService userService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(UserDTO userDTO) {
        try {
            userService.createUser(UserMapper.toModel(userDTO));
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<?> userExists(String username, String email) {
        boolean existsByUsername = userService.userExistsByUsername(username);
        boolean existsByEmail = userService.userExistsByEmail(email);
        return ResponseEntity.ok("Exists by username: " + existsByUsername + ", Exists by email: " + existsByEmail);
    }
}
