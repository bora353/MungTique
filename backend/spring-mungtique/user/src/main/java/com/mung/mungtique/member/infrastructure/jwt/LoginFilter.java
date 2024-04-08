package com.mung.mungtique.member.infrastructure.jwt;

import com.mung.mungtique.member.adaptor.in.web.dto.CustomUserDetailsDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * LoginFilter : 사용자가 로그인 시도할 때 가로채서 로그인 인증 수행
     * 인증 성공하면 JWT 토큰 생성해서 클라이언트에게 전달
     * 이 토큰으로 이후 클라이언트가 요청할 때 인증 정보로 사용됨
     */

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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
        log.info("성공!!");

        // CustomUserDetails 객체에서 사용자 이름과 역할 정보 추출
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
        response.addHeader("Authorization", "Bearer " + token);

        // TODO : JWT DB에 저장?
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
