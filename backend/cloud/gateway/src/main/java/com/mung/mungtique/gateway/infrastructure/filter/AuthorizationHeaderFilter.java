package com.mung.mungtique.gateway.infrastructure.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.netty.handler.codec.http.cookie.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpCookie;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final SecretKey secretKey;

    public AuthorizationHeaderFilter(@Value("${spring.jwt.secret}") String secret) {
        super(AuthorizationHeaderFilter.Config.class);
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Request Path: {}", request.getURI().getPath());

            // 1. OAuth 인증 (쿠키)
            HttpCookie authCookie = request.getCookies().getFirst("Oauth2-Access-Token");
            if (authCookie != null) {
                log.info("OAuth Cookie: {}", authCookie);
                if (parseClaims(authCookie.getValue()) == null) {
                    return onError(exchange, "Invalid OAuth JWT Token");
                }
                return chain.filter(exchange);
            }

            // 2. JWT 인증 (헤더)
            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return onError(exchange, "No Authorization header");
            }

            String jwt = authorizationHeader.substring(7);
            Claims claims = parseClaims(jwt);
            if (claims == null) {
                return onError(exchange, "Invalid or expired JWT token");
            }

            return chain.filter(exchange);
        });
    }

    // JWT를 검증하고, 유효하면 Claims 객체를 반환. 유효하지 않으면 null 반환
    private Claims parseClaims(String token) {
        log.info("JWT Token: {}", token);
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            log.warn("JWT token expired: {}", ex.getMessage());
        } catch (JwtException ex) {
            log.warn("Invalid JWT token: {}", ex.getMessage());
        }
        return null;
    }

    // Mono, Flux -> Spring WebFlux (클라이언트 요청에 대한 반환값)
    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.error(error);

        String jsonResponse = generateErrorResponse(error);
        DataBuffer buffer = response.bufferFactory().wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

    private String generateErrorResponse(String error) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("result", "ERROR");
        errorResponse.put("message", error);
        try {
            return new ObjectMapper().writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            return "{\"result\":\"ERROR\",\"message\":\"Internal Server Error\"}";
        }
    }

    public static class Config {
    }
}
