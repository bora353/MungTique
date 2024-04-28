package com.mung.mungtique.member.infrastructure.jwt;

import com.mung.mungtique.member.application.port.out.TokenRepoPort;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutFilter extends GenericFilter {

    private final JwtUtil jwtUtil;
    private final TokenRepoPort tokenRepoPort;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 요청이 로그아웃인지 확인 (path verify)
        String requestUri = request.getRequestURI();

        //if (!requestUri.matches("\\/logout")) { // 참고. "^\\/logout$" 정확히 "/logout" 인 경우만 체크하려는게 아니면 ^랑 $ 제거하면 됨
        if (!requestUri.matches("\\/api\\/v1\\/logout")) { // 참고. "^\\/logout$" 정확히 "/logout" 인 경우만 체크하려는게 아니면 ^랑 $ 제거하면 됨
            filterChain.doFilter(request, response);
            return;
        }

        // 요청이 로그아웃인지 확인 (method verify)
        String requestMethod = request.getMethod();

        if (!requestMethod.equals("POST")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // get refresh token
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refreshToken = cookie.getValue();
            }
        }

        // refresh null check
        if (refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            // response status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 토큰이 refresh 인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // DB에 저장되어 있는지 확인
        Boolean isExist = tokenRepoPort.existByRefreshToken(refreshToken);

        if (!isExist) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 로그아웃 진행
        // DB에서 Refresh 토큰을 제거
        log.info("로그아웃 진행 시작");
        tokenRepoPort.deleteByRefreshToken(refreshToken);

        // Refresh 토큰 Cookie 삭제!
        Cookie cookie = new Cookie("refresh", null); // null값 설정
        cookie.setMaxAge(0); // 쿠키 만료시킴
        cookie.setPath("/"); // 쿠키 전송될 경로 (모든 경로로 설정한 것)

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);

        log.info("로그아웃 성공!!");
    }
}
