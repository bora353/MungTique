package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.infrastructure.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/oauth2/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("OAuth2 로그아웃 요청됨");

        String refreshToken = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "refresh".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                ).orElse(null);

        // 토큰 레디스에서 삭제
        if (refreshToken != null) {
            tokenService.deleteRefreshToken(refreshToken);
            log.info("Deleted refresh token from Redis: {}", refreshToken);
        } else {
            log.warn("Refresh token not found in cookies.");
        }

        // 쿠키 모두 삭제
        Arrays.asList("refresh", "JSESSIONID")
                .forEach(name -> deleteCookie(response, name));

        return ResponseEntity.ok("OAuth2 logout complete");
    }

    @GetMapping("/oauth2/me")
    public ResponseEntity<String> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null) return ResponseEntity.ok("no access token");

        accessToken = accessToken.substring(7);

        try {
            Long userId = jwtUtil.extractUserId(accessToken);
            log.info("oauth2 getUserInfo userId: {}", userId);
            return ResponseEntity.ok(userId.toString());
        }  catch (JwtException ex) {
            log.error("JWT 오류: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        } catch (Exception ex) {
            log.error("서버 오류 발생: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        log.info("Cookie deleted: {}", name);
    }
}
