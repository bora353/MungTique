package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.application.port.out.TokenRepoPort;
import com.mung.mungtique.user.domain.Token;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    //private final JwtUtil jwtUtil;
    private final TokenRepoPort tokenRepoPort;

    @Value("${spring.jwt.access-expiration}")
    private String ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.refresh-expiration}")
    private String REFRESH_TOKEN_EXPIRATION_TIME;

    @Override
    public Token saveRefreshToken(String email, String refreshToken) {
        Instant now = Instant.now();

        Token token = Token.builder()
                .email(email)
                .refreshToken(refreshToken)
                .expiration(Date.from(now.plusMillis(Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME))).toString())
                .build();

        return tokenRepoPort.save(token);
    }

    /*@Override
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

            tokens.put("error", "refresh token invalid");
            return tokens;
        }

        // DB에 저장되어 있는지 확인
        Boolean isExist = tokenRepoPort.existByRefreshToken(refreshToken);
        if (!isExist) {

            tokens.put("error", "refresh token not exist");
            return tokens;
        }


        String email = jwtUtil.getEmail(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // make new JWT token
        String newAccess = jwtUtil.createJwt("access", email, role, 600000L);
        tokens.put("access", newAccess);
        // refresh rotate
        String newRefresh = jwtUtil.createJwt("refresh", email, role, 86400000L);
        tokens.put("refresh", newRefresh);

        // DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        tokenRepoPort.deleteByRefreshToken(refreshToken);
        addRefreshToken(email, newRefresh, 86400000L);

        return tokens;
    }*/

    private void addRefreshToken(String email, String newRefresh, long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Token refreshToken = Token.builder()
                .email(email)
                .refreshToken(newRefresh)
                .expiration(date.toString())
                .build();

        tokenRepoPort.save(refreshToken);
    }
}
