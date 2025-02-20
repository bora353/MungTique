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

    @Value("${spring.jwt.secret}")
    private String secret;

    public AuthorizationHeaderFilter() {
        super(AuthorizationHeaderFilter.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String servicePath = exchange.getRequest().getURI().getPath();
            log.info("serviceName: {}", servicePath);

            ServerHttpRequest request = exchange.getRequest();

            // 1. OAuth 인증 (쿠키)
            HttpCookie authCookie = request.getCookies().getFirst("Authorization");
            log.info("OAuth Cookie: {}", authCookie);
            if (authCookie != null) {
                OAuthProcess(authCookie.getValue());
                return chain.filter(exchange);
            }

            // 2. JWT 인증 (헤더)
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String jwt = authorizationHeader.replace("Bearer ", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "Invalid or expired JWT token", HttpStatus.UNAUTHORIZED);
            }
            
            /*exchange.getRequest().mutate()
                    .header("X-User-email", email)
                    .build();*/

            return chain.filter(exchange);
        });
    }

    // Mono, Flux -> Spring WebFlux (클라이언트 요청에 대한 반환값)
    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(error);

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("result", "ERROR");
        errorResponse.put("message", error);
        String jsonResponse = null;
        try {
            jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);

//        byte[] bytes = "The requested token is invalid.".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }

    private boolean isJwtValid(String jwt) {
        log.info("------ jwt : {}", jwt);
        byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        boolean returnValue = true;
        String subject = null;

        try {
            subject = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
            log.info("isJwtValid - subject(email) : {}", subject);
        } catch (ExpiredJwtException ex) {
            log.info("JWT token expired: {}", ex.getMessage());
            returnValue = false;
        } catch (JwtException ex) {
            log.info("Invalid JWT token: {}", ex.getMessage());
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

    private void OAuthProcess(String token) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.get("username", String.class);

        log.info("OAuth isJwtValid - claim(username) : {}", username);
    }

    public static class Config {
    }
}
