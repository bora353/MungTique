/*
package com.mung.mungtique.user.infrastructure.jwt;


import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// JWT 발급, 검증
@Component
public class JwtUtil {

    private SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public String createJwt(String category, String email, String role, Long expiredMs) {
        // JWT 토큰 생성 및 서명하여 반환
        return Jwts.builder()
                .claim("category", category) // access인지 refresh인지
                .claim("email", email) // 페이로드에 username, role 정보를 담아서 토큰을 생성
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 설정
                .signWith(secretKey) // 시크릿키로 서명
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String createOAuth2Jwt(String username, String role, Long expiredMs) {
        // JWT 토큰 생성 및 서명하여 반환
        return Jwts.builder()
                //.claim("category", "OAuth2") // JwtFilter에서 분류하기 위함
                .claim("username", username) // 페이로드에 username, role 정보를 담아서 토큰을 생성
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 설정
                .signWith(secretKey) // 시크릿키로 서명
                .compact();
    }
}
*/
