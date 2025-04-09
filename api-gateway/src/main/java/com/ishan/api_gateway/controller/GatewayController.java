//package com.ishan.api_gateway.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import reactor.core.publisher.Mono;
//
//@RestController
//public class GatewayController {
//
//    private final WebClient webClient;
//
//    @Autowired
//    public GatewayController(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    private String getServiceUrl(String path) {
//        if (path.startsWith("/admin")) return "http://localhost:8083" + path;
//        if (path.startsWith("/credit")) return "http://localhost:8081" + path;
//        if (path.startsWith("/debit")) return "http://localhost:8082" + path;
//        return null;
//    }
//
//    @RequestMapping("/admin/**")
//    public Mono<ResponseEntity<String>> forwardAdmin(ServerHttpRequest request) {
//        return forwardRequest(request);
//    }
//
//    @RequestMapping("/credit/**")
//    public Mono<ResponseEntity<String>> forwardCredit(ServerHttpRequest request) {
//        return forwardRequest(request);
//    }
//
//    @RequestMapping("/debit/**")
//    public Mono<ResponseEntity<String>> forwardDebit(ServerHttpRequest request) {
//        return forwardRequest(request);
//    }
//
//    private Mono<ResponseEntity<String>> forwardRequest(ServerHttpRequest request) {
//        String targetUrl = getServiceUrl(request.getURI().getPath());
//        if (targetUrl == null) {
//            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Service not found."));
//        }
//
//        WebClient.RequestHeadersSpec<?> spec = webClient.get().uri(targetUrl);
//        for (String headerName : request.getHeaders().keySet()) {
//            spec = spec.header(headerName, request.getHeaders().getFirst(headerName));
//        }
//
//        return spec.retrieve()
//                .toEntity(String.class)
//                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY)
//                        .body("Failed to forward request: " + ex.getMessage())));
//    }
//}
