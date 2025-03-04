package com.mung.mungtique.gateway.infrastructure.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.List;

@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
public class CorsGlobalConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());

        http.authorizeExchange((auth) -> auth
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/actuator/**", "/h2-console/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/user-service/join").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/user-service/mail-send").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/user-service/login").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/user-service/logout").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/user-service/reissue").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/user-service/mail-check").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/user-service/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/oauth2/authorization/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/user-service/oauth2/**").permitAll()
                        .pathMatchers("/api/v1/mungshop-service/**").permitAll()
                        .pathMatchers("/api/v1/dog-service/**").permitAll()
                        .pathMatchers("/api/v1/reservation-service/**").permitAll()
                        .pathMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
                        .pathMatchers("/api/v1/admin").hasRole("ADMIN")
                        .anyExchange().authenticated()
                );

        return http.build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        return new CorsWebFilter(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://example.com")); // TODO : 배포시 변경
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
