package com.ishan.api_gateway.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.List;
@Component
public class JwtUtil {
    private final String secret = "my-super-secret-key-that-is-secure-and-strong";
    // Use at least 256-bit key

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("permissions", List.class); // ✅ now matches the token
    }


    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token); // Will throw exception if invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
