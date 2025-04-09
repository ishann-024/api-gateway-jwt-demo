package com.ishan.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Removed FilterRegistrationBean configuration for JwtAuthenticationFilter and AuthorizationFilter
    // JwtAuthenticationFilter is already a GlobalFilter (@Component), and AuthorizationFilter is deleted
}
