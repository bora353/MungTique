package com.mung.mungtique.user.infrastructure.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.application.port.in.UserService;
import com.mung.mungtique.user.domain.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * LoginFilter : 사용자가 로그인 시도할 때 가로채서 로그인 인증 수행
     * 인증 성공하면 JWT 토큰 생성해서 클라이언트에게 전달
     * 이 토큰으로 이후 클라이언트가 요청할 때 인증 정보로 사용됨
     */

    /**
     * - Access Token : 권한이 필요한 모든 요청 헤더에 사용될 JWT로 탈취 위험을 낮추기 위해 약 10분 정도의 짧은 생명주기를 가진다.
     *      ㄴ 헤더에 발급 후 프론트에서 로컬 스토리지 저장
     * - Refresh Token : Access 토큰이 만료되었을 때 재발급 받기 위한 용도로만 사용되며 약 24시간 이상의 긴 생명주기를 가진다.
     *      ㄴ 쿠키에 발급
     */

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.userService = userService;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("email");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 추출
        String email = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("attemptAuthentication - email : {}", email);

        // 스프링 시큐리티에서 user, pass 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());

        // token에 담아서 검증하기 위해 AuthenticationManager로 전달
        return getAuthenticationManager().authenticate(authToken);
    }

    // 인증 성공시 실행되는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        String email = ((User) authentication.getPrincipal()).getUsername();
        UserEntity user = userService.getUserDetailsByEmail(email);
        userService.updateLastLoginAt(user);

        // role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        log.info("successfulAuthentication - roles : {}", roles);

        String access = jwtUtil.createToken(email, roles, "access");
        String refresh = jwtUtil.createToken(email, roles, "refresh");

        tokenService.saveRefreshToken(email, refresh);

        response.setHeader("Authorization", "Bearer " + access);
        response.addCookie(createCookie("refresh", refresh)); // refresh token -> 쿠키에
        response.setStatus(HttpStatus.OK.value()); // HTTP 상태코드 200 (OK) 설정

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userId", user.getId());

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), responseBody);

        log.info("access : {}", access);
        log.info("refresh : {}", refresh);
        log.info("successfulAuthentication - access, refresh token send Complete");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24); // 쿠키 최대 수명 : 24시간
        cookie.setPath("/"); //  쿠키의 경로를 설정 (활성화하면 쿠키가 특정 경로에서만 사용됨)
        cookie.setHttpOnly(true); // 쿠키가 HttpOnly로 설정되면, 클라이언트 측 스크립트(예: JavaScript)에서 쿠키에 접근할 수 없음 (XSS 공격을 방지하는 데 도움됨)
        //cookie.setSecure(true); // HTTPS에서만 전송 (쿠키 보안 설정)

        return cookie;
    }

    // 인증 실패시 실행되는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 인증 실패시 Response Header에 에러 메시지 전달
        response.addHeader("error", "Authentication Failed");
        log.info("unsuccessfulAuthentication");
        response.setStatus(401);
    }
}
