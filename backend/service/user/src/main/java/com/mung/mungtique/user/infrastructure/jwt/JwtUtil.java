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
import java.util.List;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    @Value("${spring.jwt.access-expiration}")
    private String ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.refresh-expiration}")
    private String REFRESH_TOKEN_EXPIRATION_TIME;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        byte[] secretKeyBytes = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = parseClaims(token);
            Date expiration = claims.getExpiration();

            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.get("username", String.class);
    }

    public String extractSubject(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = parseClaims(token);
        Object rolesObject = claims.get("roles");

        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String createToken(String email, List<String> roles, String type) {
        Instant now = Instant.now();
        long expireTime = "access".equals(type) ? Long.parseLong(ACCESS_TOKEN_EXPIRATION_TIME) : Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(email)
                .claim("category", "Local")
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expireTime)))
                .signWith(secretKey)
                .compact();
    }

    public String createOAuth2Token(String username, String role, Long userId, String type) {
        Instant now = Instant.now();
        //long expireTime = "access".equals(type) ? Long.parseLong(ACCESS_TOKEN_EXPIRATION_TIME) : Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME);

        // JWT 토큰 생성 및 서명하여 반환
        return Jwts.builder()
                .subject(userId.toString())
                .claim("username", username)
                .claim("category", "OAuth2") // JwtFilter에서 분류하기 위함
                .claim("role", role)
                .issuedAt(Date.from(now)) // 토큰 발급 시간 설정
                .expiration(Date.from(now.plusMillis(24 * 60 * 60 * 1000)))
                .signWith(secretKey) // 시크릿키로 서명
                .compact();
    }
}
