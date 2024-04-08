package com.mung.mungtique.member.infrastructure.config;

import com.mung.mungtique.member.infrastructure.jwt.JwtUtil;
import com.mung.mungtique.member.infrastructure.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf disable
        http
                .csrf((auth) -> auth.disable());

        // 폼 로그인 비활성화
        http
                .formLogin((auth) -> auth.disable());

        // HTTP 기본 인증 비활성화
        http
                .httpBasic((auth) -> auth.disable());

        // HTTP 요청에 대한 인가 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login", "/join").permitAll() // 모든 사용자 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사용자만 허용
                        .anyRequest().authenticated()); // 기타는 인증된 사용자만 허용

        // 세션 비활성화 (jwt 토큰 사용)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 로그인 필터는 UsernamePasswordAuthenticationFilter를 대체해서 실행
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
