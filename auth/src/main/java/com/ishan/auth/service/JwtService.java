package com.ishan.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class JwtService {

    private SecretKey key;
    private Map<String, List<String>> policyMap;

    @PostConstruct
    public void init() {
        // Set JWT key
        String SECRET = "my-super-secret-key-that-is-secure-and-strong";
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        // Load policy.json
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("policy.json")) {
            ObjectMapper mapper = new ObjectMapper();
            policyMap = mapper.readValue(is, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            policyMap = new HashMap<>();
        }
    }

    public String generateToken(String username) {
        List<String> permissions = policyMap.getOrDefault(username, new ArrayList<>());

        return Jwts.builder()
                .setSubject(username)
                .claim("permissions", permissions)  // ✅ Add permissions claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> extractPermissions(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("permissions", List.class);
    }

    public boolean isTokenValid(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
