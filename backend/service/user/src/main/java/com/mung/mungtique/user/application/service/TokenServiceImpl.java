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
        String refreshToken = extractRefreshToken(request).orElseThrow(() -> new NoSuchElementException("Refresh token not found"));

        validateRefreshToken(refreshToken);

        String email = jwtUtil.extractUsername(refreshToken);
        List<String> roles = jwtUtil.extractRoles(refreshToken);
        String newAccess = jwtUtil.generateToken(email, roles,"access");
        String newRefresh = jwtUtil.generateToken(email, roles, "refresh");

        // DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        saveRefreshToken(email, newRefresh);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", newAccess);
        tokens.put("refresh", newRefresh);

        return tokens;
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        String email = jwtUtil.extractUsername(refreshToken);
        refreshTokenRepoPort.deleteById(email);
    }

    @Override
    @Transactional
    public RefreshToken saveRefreshToken(String email, String newRefreshToken) {
        refreshTokenRepoPort.deleteById(email);
        RefreshToken token = createNewToken(email, newRefreshToken);
        return refreshTokenRepoPort.save(token);
    }

    private void validateRefreshToken(String refreshToken) {
        if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException ("Invalid or expired refresh token");
        }

        String email = jwtUtil.extractUsername(refreshToken);
        RefreshToken storedRefreshToken = refreshTokenRepoPort.findById(email)
                .orElseThrow(() -> new RuntimeException("Refresh token not found in the database"));

        if (!refreshToken.equals(storedRefreshToken.getRefreshToken())) {
            throw new RuntimeException("Refresh token mismatch");
        }
    }

    private Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "refresh".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst());
    }

    private RefreshToken createNewToken(String email, String refreshToken) {
        long expiration = Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME);
        return new RefreshToken(email, refreshToken, expiration);
    }
}
