package com.ishan.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

//@Component
//public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        String token = authHeader.substring(7);
//        try {
//            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            String username = claims.getSubject();
//            List<String> roles = claims.get("roles", List.class);
//            String path = exchange.getRequest().getURI().getPath();
//
//            // Role-based access control
//            if (username.equals("user1")) {
//                return chain.filter(exchange); // full access
//            } else if (username.equals("user2") &&
//                    (roles.contains("CREDIT") || roles.contains("DEBIT")) &&
//                    (path.startsWith("/credit") || path.startsWith("/debit"))) {
//                return chain.filter(exchange);
//            } else if (username.equals("user3") &&
//                    roles.contains("ADMIN") &&
//                    path.startsWith("/admin")) {
//                return chain.filter(exchange);
//            } else {
//                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//                return exchange.getResponse().setComplete();
//            }
//
//        } catch (Exception e) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}
