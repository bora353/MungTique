package com.mung.mungtique.user.infrastructure.jwt;

import com.mung.mungtique.user.adaptor.in.web.dto.CustomOAuth2User;
import com.mung.mungtique.user.adaptor.in.web.dto.UserDTO;
import com.mung.mungtique.user.domain.Authority;
import com.mung.mungtique.user.domain.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

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
        // 재로그인 무한 루프 오류 해결 (재로그인 -> JWT 만료시 거절 -> OAuth2 로그인 실패 -> 재요청의 무한루프)
        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/api\\/v1\\/(join|login|reissue|logout|mail-check|mail-send)(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (requestUri.matches("^\\/user\\/login(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 시작

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            OAuthProcess(request, response, filterChain, cookies);
        }

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        if (accessToken != null) {
            StandardProcess(request, response, filterChain, accessToken);
        }

        /*-------이전코드----------// request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            log.info("token null");
            filterChain.doFilter(request, response); // 다음 필터로 넘김

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

        filterChain.doFilter(request, response);*/
    }

    private void StandardProcess(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String accessToken) throws IOException, ServletException {
        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //  response body
            PrintWriter writer = response.getWriter(); // HTTP 응답 본문에 데이터를 쓰기 위함
            writer.print("access token expired");

            // response status code (만료 시, 프론트엔드와 코드 맞추기 -> 프론트엔드가 401 받으면 refresh 토큰 서버로 전달)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return;
        }

        // username, role 값을 획득
        String email = jwtUtil.getEmail(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserEntity userEntity = UserEntity.builder().username(email).role(Authority.valueOf(role)).build();

//        User user = new User();
//        user.setUsername(email);
//        user.setRole(role);

        //매우잠시 CustomUserDetailsDTO customUserDetails = new CustomUserDetailsDTO(userEntity);

        //매우잠시 Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //매우 잠시 SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private void OAuthProcess(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Cookie[] cookies) throws ServletException, IOException {
        // cookie 불러온 후 Authorization Key에 담긴 쿠키 찾음
        String authorization = null;
        //Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            log.info(cookie.getName());
            if (cookie.getName().equals("Authorization")) {

                authorization = cookie.getValue();
            }
        }

        // Authorization 헤더 검증
        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization;

        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role1 = jwtUtil.getRole(token);

        UserDTO userDTO = UserDTO.builder()
                        .username(username)
                        .role(Authority.valueOf(role1))
                        .build();

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
