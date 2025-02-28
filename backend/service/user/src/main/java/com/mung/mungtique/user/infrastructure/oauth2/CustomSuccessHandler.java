package com.mung.mungtique.user.infrastructure.oauth2;

import com.mung.mungtique.user.adaptor.in.web.dto.CustomOAuth2User;
import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.infrastructure.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${spring.jwt.access-expiration}")
    private String ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.refresh-expiration}")
    private String REFRESH_TOKEN_EXPIRATION_TIME;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // OAuth2 User 정보 가져오기
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getName();

        // 유저 역할(Role) 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // JWT 액세스 토큰 & 리프레시 토큰 생성
        String accessToken = jwtUtil.createOAuth2Jwt(username, role, "access");
        String refreshToken = jwtUtil.createOAuth2Jwt(username, role, "refresh");

        // 리프레시 토큰 Redis 저장
        tokenService.saveRefreshToken(username, refreshToken);

        response.addCookie(createCookie("Oauth2-Access-Token", accessToken, (int) (Long.parseLong(ACCESS_TOKEN_EXPIRATION_TIME) / 1000)));
        response.addCookie(createCookie("Oauth2-Refresh-Token", refreshToken, (int) (Long.parseLong(REFRESH_TOKEN_EXPIRATION_TIME) / 1000)));

        // 클라이언트 리다이렉트 (OAuth2 로그인 완료)
        response.sendRedirect(allowedOrigins);
    }

    private Cookie createCookie(String key, String value, int expiration) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiration); // 초단위 설정 필요
        // cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
