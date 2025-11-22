package com.minicloud.security;

import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTutil {
    // Clave en Base64
    private final String SECRET_KEY = "MjFjMjM4NjY0NTc5OTg5MjAyNDU2Yzg5MTIzNDU2Nzg=";   
    private final long EXPIRATION_TIME = 86400000; // 24H

    private byte[] getSecretKeyBytes() {
        return Base64.getDecoder().decode(SECRET_KEY);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(getSecretKeyBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(getSecretKeyBytes()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(getSecretKeyBytes()))
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}