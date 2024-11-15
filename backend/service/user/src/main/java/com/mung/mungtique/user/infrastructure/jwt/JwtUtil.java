package com.mung.mungtique.user.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    @Value("${spring.jwt.access-expiration}")
    private String ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.refresh-expiration}")
    private String REFRESH_TOKEN_EXPIRATION_TIME;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();

            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public String generateToken(String email, String type) {
        Instant now = Instant.now();
        long expireTime = "access".equals(type) ? Long.parseLong(ACCESS_TOKEN_EXPIRATION_TIME) : Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expireTime)))
                .signWith(secretKey)
                .compact();
    }

    public String createOAuth2Jwt(String username, String role) {
        Instant now = Instant.now();
        long expireTime = Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME);

        // JWT 토큰 생성 및 서명하여 반환
        return Jwts.builder()
                //.claim("category", "OAuth2") // JwtFilter에서 분류하기 위함
                .claim("username", username) // 페이로드에 username, role 정보를 담아서 토큰을 생성
                .claim("role", role)
                .issuedAt(Date.from(now)) // 토큰 발급 시간 설정
                .expiration(Date.from(now.plusMillis(expireTime))) // 토큰 만료 시간 설정
                .signWith(secretKey) // 시크릿키로 서명
                .compact();
    }
}
