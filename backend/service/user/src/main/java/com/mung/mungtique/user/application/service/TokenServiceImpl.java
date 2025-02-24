package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.application.port.out.RefreshTokenRepoPort;
import com.mung.mungtique.user.domain.RefreshToken;
import com.mung.mungtique.user.infrastructure.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepoPort refreshTokenRepoPort;

    @Value("${spring.jwt.refresh-expiration}")
    private String REFRESH_TOKEN_EXPIRATION_TIME;

    @Override
    @Transactional
    public Map<String, String> reissueToken(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromRequest(request).orElseThrow(() -> new NoSuchElementException("Refresh token not found"));

        validateRefreshToken(refreshToken);

        String email = jwtUtil.extractUsername(refreshToken);
        List<String> roles = jwtUtil.extractRoles(refreshToken);
        String newAccess = jwtUtil.generateToken(email, roles,"access");
        String newRefresh = jwtUtil.generateToken(email, roles, "refresh");

        // DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        deleteAndSaveRefreshToken(refreshToken, email, newRefresh);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", newAccess);
        tokens.put("refresh", newRefresh);

        return tokens;
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepoPort.deleteRefreshToken(refreshToken);
    }

    @Override
    @Transactional
    public RefreshToken saveRefreshToken(String email, String refreshToken) {
        refreshTokenRepoPort.deleteById(email);
        RefreshToken token = createNewToken(email, refreshToken);
        return refreshTokenRepoPort.save(token);
    }

    private void validateRefreshToken(String refreshToken) {
        if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException ("Invalid or expired refresh token");
        }

        Boolean isExist = refreshTokenRepoPort.existByRefreshToken(refreshToken);
        if (!isExist) {
            throw new RuntimeException("Refresh token not found in the database");
        }
    }

    private Optional<String> extractRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("refresh".equals(cookie.getName())) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }

    private void deleteAndSaveRefreshToken(String refreshToken, String email, String newRefresh) {
        refreshTokenRepoPort.deleteRefreshToken(refreshToken);
        refreshTokenRepoPort.deleteById(email);
        RefreshToken token = createNewToken(email, newRefresh);
        refreshTokenRepoPort.save(token);
    }

    private RefreshToken createNewToken(String email, String refreshToken) {
        long expiration = Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME); // 만료 시간 (초 단위)
        return new RefreshToken(email, refreshToken, expiration);
    }
}
