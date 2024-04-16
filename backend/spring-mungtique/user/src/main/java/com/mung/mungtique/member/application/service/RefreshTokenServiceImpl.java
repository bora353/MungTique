package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.application.port.in.RefreshTokenService;
import com.mung.mungtique.member.infrastructure.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtUtil jwtUtil;

    // TODO : expiration time 환경변수로
/*    @Value("${spring.jwt.access-expiration}")
    private final String accessExpireTime;

    @Value("${spring.jwt.refresh-expiration}")
    private final int refreshExpireTime;*/

    @Override
    public Map<String, String> reissueToken(HttpServletRequest request) {
        Map<String, String> tokens = new HashMap<>();

        //get refresh token
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {

            tokens.put("error", "refresh token null");
            return tokens;
        }

        // expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {

            tokens.put("error", "refresh token expired");
            return tokens;
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {

            tokens.put("error", "invalid refresh token");
            return tokens;
        }

        String email = jwtUtil.getEmail(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // make new JWT token
        tokens.put("access", jwtUtil.createJwt("access", email, role, 600000L));
        // refresh rotate
        tokens.put("refresh", jwtUtil.createJwt("refresh", email, role, 86400000L));

        return tokens;
    }
}
