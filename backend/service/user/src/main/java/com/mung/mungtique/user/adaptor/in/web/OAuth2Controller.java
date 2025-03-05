package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.infrastructure.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        // 토큰 레디스에서 삭제
        String refreshToken = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "Oauth2-Access-Token".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                ).orElse(null);
        tokenService.deleteRefreshToken(refreshToken);

        // 쿠키 모두 삭제
        Arrays.asList("Oauth2-Access-Token", "JSESSIONID")
                .forEach(name -> deleteCookie(response, name));

        return ResponseEntity.ok("OAuth2 logout complete");
    }

    @GetMapping("/oauth2/me")
    public ResponseEntity<String> getUserInfo(@CookieValue(name = "Oauth2-Access-Token", required = false) String accessToken,
                                                                                            HttpServletResponse response) {
        log.info("check Oauth2 access token : {}", accessToken);

        if (accessToken == null) return ResponseEntity.ok("no access token");

        try {
            String userId = jwtUtil.extractSubject(accessToken);
            log.info("인증된 userId: {}", userId);
            return ResponseEntity.ok(userId);
        } catch (ExpiredJwtException ex) {
            log.warn("Access Token 만료됨: {}", ex.getMessage());
            Arrays.asList("Oauth2-Access-Token", "JSESSIONID")
                    .forEach(name -> deleteCookie(response, name));
            return ResponseEntity.ok("expired");
        } catch (Exception ex) {
            log.error("서버 오류 발생: {}", ex.getMessage());
            return ResponseEntity.ok("error");
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
