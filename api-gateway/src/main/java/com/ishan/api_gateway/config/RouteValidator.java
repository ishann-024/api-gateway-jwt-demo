package com.ishan.api_gateway.config;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.web.server.ServerWebExchange;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/auth/token"
    );

    public Predicate<ServerWebExchange> isSecured = exchange ->
            openApiEndpoints.stream()
                    .noneMatch(uri -> exchange.getRequest().getURI().getPath().contains(uri));
}
