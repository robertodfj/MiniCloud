package com.minicloud.service;

import java.io.Writer;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SimpleSMTPHeader;
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
            try {
                String sender = ""; // Correo de minicloud
                String password = ""; // Meter dentro de aplication properties
                String receiver = user.getEmail();
                String server = "smtp.gmail.com";

                AuthenticatingSMTPClient smtp = new AuthenticatingSMTPClient("TLS", true);
                smtp.setDefaultTimeout(2000);
                smtp.connect(server, 465);
                smtp.ehlo("localhost");

                smtp.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, sender, password);
                smtp.setSender(sender);
                smtp.addRecipient(receiver);

                String subject = "Token de autenticación Minicloud";
                String message = "Tu token de autenticación es: " + token;

                SimpleSMTPHeader header = new SimpleSMTPHeader(sender, receiver, subject);
                Writer wr = smtp.sendMessageData();
                if (wr != null) {
                    wr.write(header.toString());
                    wr.write(message);
                    wr.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
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
