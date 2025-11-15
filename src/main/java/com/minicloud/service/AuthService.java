package com.minicloud.service;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SimpleSMTPHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.mail.host}")
    private String server;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.password}")
    private String password;

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
    if (user == null) return;

    int token = (int) (Math.random() * 900000) + 100000;
    user.setAuthenticationToken(token);
    userRepository.save(user);

    try {
        String receiver = user.getEmail();

        // Elegir modo SSL/TLS según puerto
        String mode = (port == 465) ? "SSL" : "TLS";

        AuthenticatingSMTPClient smtp = new AuthenticatingSMTPClient(mode, true);
        smtp.setDefaultTimeout(5000);
        smtp.connect(server, port);
        smtp.ehlo("localhost");

        // Autenticación
        if (!smtp.auth(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN, sender, password)) {
            throw new RuntimeException("Error autenticando en el servidor SMTP");
        }

        smtp.setSender(sender);
        smtp.addRecipient(receiver);

        String subject = "Token de autenticación Minicloud";
        String message = "Tu token de autenticación es: " + token;

        SimpleSMTPHeader header = new SimpleSMTPHeader(sender, receiver, subject);

        try (Writer wr = smtp.sendMessageData()) {
            if (wr == null) {
                throw new RuntimeException("Error enviando los datos del mensaje SMTP");
            }
            
            wr.write(header.toString());
            wr.write(message);
        }

        // Finalizar envío y cerrar conexión correctamente
        smtp.completePendingCommand();
        smtp.logout();
        smtp.disconnect();

        System.out.println("Correo enviado correctamente a: " + receiver);

    } catch (IOException | RuntimeException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException e) {
        System.out.println("Error al enviar el email: " + e.getMessage());
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
