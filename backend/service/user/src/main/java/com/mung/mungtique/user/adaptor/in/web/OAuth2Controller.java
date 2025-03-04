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
import org.springframework.web.bind.annotation.*;

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

        // 리프레시 토큰 레디스에서 삭제
        String refreshToken = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "Oauth2-Refresh-Token".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                ).orElse(null);
        tokenService.deleteRefreshToken(refreshToken);

        // 쿠키 모두 삭제
        Arrays.asList("Oauth2-Access-Token", "Oauth2-Refresh-Token", "JSESSIONID")
                .forEach(name -> deleteCookie(response, name));

        return ResponseEntity.ok("OAuth2 logout complete");
    }

    @GetMapping("/oauth2/me")
    public ResponseEntity<String> getUserInfo(@CookieValue(name = "Oauth2-Access-Token", required = false) String accessToken) {
        log.info("check Oauth2 access token : {}", accessToken);

        if (accessToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("false");

        try {
            String userId = jwtUtil.extractUserId(accessToken);
            log.info("인증된 userId: {}", userId);
            return ResponseEntity.ok(userId);
        } catch (Exception ex) {
            log.warn("Access Token 만료됨: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("expired");
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
