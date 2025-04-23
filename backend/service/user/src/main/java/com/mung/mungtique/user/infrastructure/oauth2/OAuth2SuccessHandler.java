package com.mung.mungtique.user.infrastructure.oauth2;

import com.mung.mungtique.user.adaptor.in.web.dto.CustomOAuth2User;
import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.application.port.in.UserService;
import com.mung.mungtique.user.infrastructure.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final UserService userService;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // OAuth2 User 정보 가져오기
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String email = customUserDetails.getName();
        Long userId = customUserDetails.getUserId();

        // 유저 역할(Role) 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // JWT 액세스 토큰 생성
        String accessToken = jwtUtil.createOAuth2Token(email, roles, userId, "access");
        String refreshToken = jwtUtil.createOAuth2Token(email, roles, userId, "refresh");

        // 토큰 Redis 저장
        tokenService.saveRefreshToken(email, refreshToken);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(createCookie("refresh", refreshToken)); // refresh token -> 쿠키에
        response.setStatus(HttpStatus.OK.value()); // HTTP 상태코드 200 (OK) 설정

        // 클라이언트 리다이렉트 (OAuth2 로그인 완료)
        response.sendRedirect(allowedOrigins + "/oauth2/redirect?accessToken=" + accessToken);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24); // 초단위 설정 필요
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);

        return cookie;
    }
}
