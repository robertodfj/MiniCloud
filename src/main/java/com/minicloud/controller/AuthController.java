package com.minicloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minicloud.dto.AuthRequest;
import com.minicloud.dto.AuthResponse;
import com.minicloud.model.User;
import com.minicloud.repository.ROLErepository;
import com.minicloud.repository.UserRepository;
import com.minicloud.security.JWTutil;
import com.minicloud.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JWTutil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private ROLErepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
        userService.generateToken(user.getEmail());
        return "Usuario registrado correctamente, revisa el email para el token de autenticación.";
    }

    @PostMapping("/authenticate/{email}")
    public boolean authenticate(@PathVariable String email, @RequestBody int token) {
        userService.authenticateUser(email, token);
        return true;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        // Comprobar si esta autenticado

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}