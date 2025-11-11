package com.minicloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minicloud.model.User;
import com.minicloud.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private static UserRepository userRepository;

    public static User createUser(User user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use: " + email);
            
        }
        return userRepository.save(user);
    }

    public static User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public static User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public static boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public static boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
