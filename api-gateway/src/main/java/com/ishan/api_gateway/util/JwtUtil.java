package com.ishan.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.List;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    // Minimum 256-bit secret for HMAC SHA-256
    private final String secret = "my-super-secret-key-that-is-secure-and-strong";

    private Key signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        log.info("🔑 JwtUtil initialized with secure key");
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        try {
            return extractAllClaims(token).getSubject();
        } catch (Exception e) {
            log.warn("❌ Failed to extract username: {}", e.getMessage());
            return null;
        }
    }

    public List<String> extractRoles(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Object permissions = claims.get("permissions");
            if (permissions instanceof List<?>) {
                return ((List<?>) permissions).stream()
                        .map(Object::toString)
                        .toList(); // Safely cast to List<String>
            }
            return Collections.emptyList();
        } catch (Exception e) {
            log.warn("❌ Failed to extract roles: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token); // Throws if invalid/expired
            return true;
        } catch (Exception e) {
            log.warn("❌ Invalid JWT: {}", e.getMessage());
            return false;
        }
    }
}
