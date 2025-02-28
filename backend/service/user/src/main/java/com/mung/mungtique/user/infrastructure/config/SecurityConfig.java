package com.mung.mungtique.user.infrastructure.config;

import com.mung.mungtique.user.application.port.in.CustomOauth2UserService;
import com.mung.mungtique.user.application.port.in.TokenService;
import com.mung.mungtique.user.application.port.in.UserService;
import com.mung.mungtique.user.infrastructure.jwt.CustomLogoutHandler;
import com.mung.mungtique.user.infrastructure.jwt.JwtUtil;
import com.mung.mungtique.user.infrastructure.jwt.LoginFilter;
import com.mung.mungtique.user.infrastructure.oauth2.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    private final CustomLogoutHandler logoutSuccessHandler;
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf(csrf -> csrf.disable()) // csrf disable (jwt 방식으로 stateless로 관리할 것이기에 disable)
                .formLogin(formLogin -> formLogin.disable()) // 폼 로그인 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP 기본 인증 비활성화
                .cors(cors -> cors.disable()) // gateway에 있기 때문에 중복 필요 없음

                // 세션 비활성화 (jwt 토큰 사용)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // HTTP 요청에 대한 인가 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/join", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/reissue", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()

                        .requestMatchers("/actuator/**", "/h2-console/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/join", "/reissue").permitAll()

                        .requestMatchers("/**").access(
                                new WebExpressionAuthorizationManager("hasIpAddress('localhost') " +
                                        "or hasIpAddress('127.0.0.1') or hasIpAddress('192.168.219.107')")) // host pc ip address

                        .anyRequest().authenticated()
                )

                // jwt 로그인 필터 적용 (UsernamePasswordAuthenticationFilter를 대체해서 실행)
                .addFilter(getAuthenticationFilter(authenticationManager))

                // 로그아웃 설정
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutUrl("/logout")
                                .addLogoutHandler(logoutSuccessHandler)
                                .logoutSuccessHandler(logoutSuccessHandler)
                                .deleteCookies("refresh")
                                .permitAll()
                )

                // OAuth2 로그인 설정
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOauth2UserService))
                        .successHandler(customSuccessHandler));

        return http.build();
    }

    private LoginFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new LoginFilter(authenticationManager, userService, tokenService, jwtUtil);
    }
}
