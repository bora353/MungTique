package com.mung.mungtique.gateway.infrastructure.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter
        return ((exchange, chain) -> {

            if (exchange.getRequest().getURI().getPath().equals("/api/v1/user-service/join")
                || exchange.getRequest().getURI().getPath().equals("/api/v1/user-service/login")
                || exchange.getRequest().getURI().getPath().equals("/api/v1/user-service/logout")) {
                return chain.filter(exchange);
            }

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Global Filter Start : request id -> {}", request.getId());
            }

            // Custom Post Filter (Mono : 비동기방식 지원)
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Global Filter End : response code -> {}", response.getStatusCode());
                }
            }));
        });
    }

    @Data
    public static class Config {
        // Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
