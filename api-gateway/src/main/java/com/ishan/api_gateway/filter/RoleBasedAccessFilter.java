package com.ishan.api_gateway.filter;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class RoleBasedAccessFilter implements GlobalFilter, Ordered {

    private final Logger log = LoggerFactory.getLogger(RoleBasedAccessFilter.class);

    @PostConstruct
    public void init() {
        log.info("✅ RoleBasedAccessFilter initialized");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();

        String rolesHeader = request.getHeaders().getFirst("X-Roles");
        List<String> roles = rolesHeader != null ? Arrays.asList(rolesHeader.split(",")) : List.of();

        log.info("🔍 Path: {}", path);
        log.info("🎭 X-Roles Header: {}", rolesHeader);
        log.info("✅ Parsed Roles: {}", roles);

        if (path.startsWith("/credit") || path.startsWith("/debit")) {
            if (!roles.contains("CREDIT") && !roles.contains("DEBIT")) {
                log.warn("🚫 Access denied to {} for roles: {}", path, roles);
                return forbidden(exchange, "Access denied to " + path);
            }
        } else if (path.startsWith("/admin")) {
            if (!roles.contains("ADMIN")) {
                log.warn("🚫 Access denied to {} for roles: {}", path, roles);
                return forbidden(exchange, "Access denied to " + path);
            }
        }

        log.info("✅ Access granted to {} for roles: {}", path, roles);
        return chain.filter(exchange);
    }

    private Mono<Void> forbidden(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0; // Run AFTER JwtAuthFilter
    }
}
