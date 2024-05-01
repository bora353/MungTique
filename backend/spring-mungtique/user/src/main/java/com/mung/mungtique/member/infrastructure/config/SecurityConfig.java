package com.mung.mungtique.member.infrastructure.config;

import com.mung.mungtique.member.application.port.out.TokenRepoPort;
import com.mung.mungtique.member.infrastructure.jwt.CustomLogoutFilter;
import com.mung.mungtique.member.infrastructure.jwt.JwtFilter;
import com.mung.mungtique.member.infrastructure.jwt.JwtUtil;
import com.mung.mungtique.member.infrastructure.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final TokenRepoPort tokenRepoPort;


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

        // cors 설정 (인증과 인가가 필요한 요청에 대한 응답 헤더 설정)
        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();

                                configuration.setAllowedOrigins(Collections.singletonList(allowedOrigins));
                                configuration.setAllowedMethods(Collections.singletonList("*")); // HTTP 메서드
                                configuration.setAllowCredentials(true); // 자격 증명(쿠기, HTTP 인증) 사용
                                configuration.setAllowedHeaders(Collections.singletonList("*")); // 요청 헤더 설정

                                configuration.setMaxAge(3600L); // 요청을 캐시할 수 있는 시간 (1시간)

                                configuration.setExposedHeaders(Collections.singletonList("access")); // 응답헤더로 노출할 헤더
                                return configuration;
                            }
                        }));

        // 폼 로그인 비활성화
        http
                .formLogin((auth) -> auth.disable());

        // HTTP 기본 인증 비활성화
        http
                .httpBasic((auth) -> auth.disable());

        // HTTP 요청에 대한 인가 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/api/v1/", "/api/v1/login", "/api/v1/join", "/swagger-ui/**", "/v3/api-docs/**", "/logout","/api/v1/mail-check","/api/v1/mail-send").permitAll() // 모든 사용자 허용
                        .requestMatchers("/api/v1/reissue").permitAll() // 모든 사용자 허용
                        .requestMatchers("/api/v1/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사용자만 허용
                        .anyRequest().authenticated()); // 기타는 인증된 사용자만 허용


        // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가 
        // JWT 토큰이 유효하지 않으면 불필요한 로그인 시도 방지
        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

        // 로그인 필터는 UsernamePasswordAuthenticationFilter를 대체해서 실행
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, tokenRepoPort), UsernamePasswordAuthenticationFilter.class);

        // 세션 비활성화 (jwt 토큰 사용)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 실제 logoutFilter 전에 customLogoutFilter가 먼저 실행됨
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, tokenRepoPort), LogoutFilter.class);

        return http.build();
    }
}
