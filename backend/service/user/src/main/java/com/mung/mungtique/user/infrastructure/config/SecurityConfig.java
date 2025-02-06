package com.mung.mungtique.user.infrastructure.config;

import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.application.port.in.UserService;
import com.mung.mungtique.user.application.service.CustomOauth2UserServiceImpl;
import com.mung.mungtique.user.infrastructure.jwt.CustomLogoutHandler;
import com.mung.mungtique.user.infrastructure.jwt.JwtUtil;
import com.mung.mungtique.user.infrastructure.jwt.LoginFilter;
import com.mung.mungtique.user.infrastructure.oauth2.CustomSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    private final CustomLogoutHandler logoutSuccessHandler;

    private final CustomOauth2UserServiceImpl customOauth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // csrf disable (jwt 방식으로 stateless로 관리할 것이기에 disable)
        http.csrf((auth) -> auth.disable());

        // cors 설정 (인증과 인가가 필요한 요청에 대한 응답 헤더 설정)
//        http
//                .cors((cors) -> cors
//                        .configurationSource(new CorsConfigurationSource() {
//                            @Override
//                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                                CorsConfiguration configuration = new CorsConfiguration();
//
//                                configuration.setAllowedOrigins(Arrays.asList("http://localhost:8000", allowedOrigins));
//
//                                configuration.setAllowedMethods(Collections.singletonList("*")); // HTTP 메서드
//                                configuration.setAllowCredentials(true); // 자격 증명(쿠기, HTTP 인증) 사용
//                                configuration.setAllowedHeaders(Collections.singletonList("*")); // 요청 헤더 설정
//
//                                configuration.setMaxAge(3600L); // 요청을 캐시할 수 있는 시간 (1시간)
//
//                                configuration.setExposedHeaders(Arrays.asList("Authorization", "Set-Cookie"));
//
//                                return configuration;
//                            }
//                        }));

        // 폼 로그인 비활성화
        http.formLogin((auth) -> auth.disable());

        // HTTP 기본 인증 비활성화
        http.httpBasic((auth) -> auth.disable());

        // HTTP 요청에 대한 인가 설정
        http.authorizeHttpRequests((auth) -> auth
                                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/join", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/reissue", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                                .requestMatchers("/**").access(
                                        new WebExpressionAuthorizationManager("hasIpAddress('localhost') " +
                                                "or hasIpAddress('127.0.0.1') or hasIpAddress('192.168.219.107')")) // host pc ip address
                                .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 로그인필터 적용 (UsernamePasswordAuthenticationFilter를 대체해서 실행)
        http.addFilter(getAuthenticationFilter(authenticationManager));

        // 로그아웃
        http
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutUrl("/logout")
                                .addLogoutHandler(logoutSuccessHandler)
                                .logoutSuccessHandler(logoutSuccessHandler)
                                .deleteCookies("refresh")
                                .permitAll()
                );

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin())); // Clickjacking 공격 방지

        /*http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/api/v1/", "/api/v1/user/login", "/api/v1/join", "/swagger-ui/**",
                                "/v3/api-docs/**", "/user/logout","/api/v1/email-check","/api/v1/email-send").permitAll() // 모든 사용자 허용

                        .requestMatchers("/api/v1/reissue").permitAll() // 모든 사용자 허용
                        .requestMatchers("/api/v1/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사용자만 허용
                        .anyRequest().authenticated()); // 기타는 인증된 사용자만 허용*/

        // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
        // JWT 토큰이 유효하지 않으면 불필요한 로그인 시도 방지
        /*http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);*/

        // 세션 비활성화 (jwt 토큰 사용)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 실제 logoutFilter 전에 customLogoutFilter가 먼저 실행됨
        /*http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, tokenRepoPort), LogoutFilter.class);*/

        // oauth2 ------------------------------------
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                                                         .userService(customOauth2UserService))
                        .successHandler(customSuccessHandler));

 /*   잠깐만 주석    http
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);*/

        return http.build();
    }

    private LoginFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        return new LoginFilter(authenticationManager, userService, tokenService, jwtUtil);
    }
}
