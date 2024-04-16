package com.mung.mungtique.member.infrastructure.jwt;

import com.mung.mungtique.member.adaptor.in.web.dto.CustomUserDetailsDTO;
import com.mung.mungtique.member.application.port.out.TokenRepoPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

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

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TokenRepoPort tokenRepoPort;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, TokenRepoPort tokenRepoPort) {
        setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.tokenRepoPort = tokenRepoPort;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/login", "POST")); // 로그인 경로 변경
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

        log.info("email : {}, password : {}", email, password);

        // 스프링 시큐리티에서 user, pass 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        // token에 담아서 검증하기 위해 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    // 인증 성공시 실행되는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        /*// CustomUserDetails 객체에서 사용자 이름과 역할 정보 추출
        CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) authentication.getPrincipal();

        String email = customUserDetailsDTO.getEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 인증 성공시 JWT 토큰 생성 및 만료 시간 설정
        String token = jwtUtil.createJwt(email, role, 60 * 60 * 1000L); // 1시간
        log.info("JWT 토큰 생성 : {}", token); // 매번 다른 토큰 생성


        // 생성된 JWT 토큰을 Response Header에 담아서 클라이언트에 전달
        response.addHeader("Authorization", "Bearer " + token);*/

        log.info("access, refresh 토큰 발급 성공!!");

        // 유저 정보
        CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) authentication.getPrincipal();
        String email = customUserDetailsDTO.getEmail();

        // role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access", email, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", email, role, 86400000L);

        // Refresh Token DB 저장


        // 응답 설정
        response.setHeader("access", access); // access token : 응답 헤더에
        response.addCookie(createCookie("refresh", refresh)); // refresh token : 쿠키에
        response.setStatus(HttpStatus.OK.value()); // HTTP 상태코드 200 (OK) 설정
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); // 쿠키 최대 수명 : 24시간
        //cookie.setSecure(true); // 쿠키 보안설정 (활성화하면 HTTPS를 통해서만 쿠키가 전송)
        //cookie.setPath("/"); //  쿠키의 경로를 설정 (활성화하면 쿠키가 특정 경로에서만 사용됨)
        cookie.setHttpOnly(true); // 쿠키가 HttpOnly로 설정되면, 클라이언트 측 스크립트(예: JavaScript)에서 쿠키에 접근할 수 없음 (XSS 공격을 방지하는 데 도움됨)

        return cookie;
    }

    // 인증 실패시 실행되는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 인증 실패시 Response Header에 에러 메시지 전달
        // response.addHeader("error", "Authentication Failed");
        log.info("실패ㅠㅠ");
        response.setStatus(401);
    }
}
