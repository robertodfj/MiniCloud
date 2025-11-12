package com.minicloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minicloud.model.User;
import com.minicloud.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use: " + email);
            
        }
        return userRepository.save(user);
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void generateToken(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            int token = (int)(Math.random() * 900000) + 100000; // Genera un token de 6 dígitos
            user.setAuthenticationToken(token);
            userRepository.save(user);
            
            // Logica para enviarel el token via email
        }
    }

    public void authenticateUser(String email, int token) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && user.getAuthenticationToken() == token) {
            user.setAuthenticated(true);
            userRepository.save(user);
        }
    }

}
