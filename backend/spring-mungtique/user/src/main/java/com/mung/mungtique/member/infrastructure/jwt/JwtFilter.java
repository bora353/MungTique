package com.mung.mungtique.member.infrastructure.jwt;

import com.mung.mungtique.member.adaptor.in.web.dto.CustomUserDetailsDTO;
import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    /**
     * JwtFilter : LoginFilter에서 생성된 JWT 검증하는 역할
     * 클라이언트가 보낸 모든 요청을 처리할 때마다 실행
     */

    private final JwtUtil jwtUtil;
    
    // HTTP 요청 올때마다 호출 (필터링 작업)
    // client가 보낸 jwt 검증하고, 사용자 정보 추출해서 스프링 시큐리티 인증 메커니즘에 적용
    // 인증된 사용자에게 접근 허용
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.info("token null");
            filterChain.doFilter(request, response);

            // 조건이 해당되면 메서드 종료 (필수)
            return;
        }

        log.info("authorization now");

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            log.info("token expired");
            filterChain.doFilter(request, response);

            // 조건 해당되면 메서드 종료 (필수)
            return;
        }

        // 토큰에서 username과 role 획득
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        // userEntity를 생성하여 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(null); // JwtFilter에서는 비밀번호를 사용하지 않음
        userEntity.setRole(role);

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetailsDTO customUserDetailsDTO = new CustomUserDetailsDTO(userEntity);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetailsDTO, null, customUserDetailsDTO.getAuthorities());
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
