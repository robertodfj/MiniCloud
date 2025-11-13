package com.minicloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minicloud.dto.AuthRequest;
import com.minicloud.dto.AuthResponse;
import com.minicloud.model.User;
import com.minicloud.repository.ROLErepository;
import com.minicloud.repository.UserRepository;
import com.minicloud.security.JWTutil;

@Service
public class AuthService {
    
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JWTutil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private ROLErepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public String register(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(roleRepository.findByName("ROLE_USER"));
            userRepository.save(user);
            generateToken(user.getEmail());
            return "Usuario registrado correctamente, revisa el email para el token de autenticación.";
        } catch (Exception e) {
            return "Error al registrar el usuario: " + e.getMessage();
        }
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null || !user.isAuthenticated()) {
            throw new IllegalArgumentException("Usuario no autenticado o no existe.");
        }

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }

    public void generateToken(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            int token = (int)(Math.random() * 900000) + 100000; // Genera un token de 6 dígitos
            user.setAuthenticationToken(token);
            userRepository.save(user);
            
            // Logica para enviar el token via email
        }
    }

    public boolean authenticateUser(String email, int token) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && user.getAuthenticationToken() == token) {
            user.setAuthenticated(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
        
}
