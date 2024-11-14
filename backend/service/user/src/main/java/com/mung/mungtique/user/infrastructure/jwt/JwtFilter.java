/*
package com.mung.mungtique.user.infrastructure.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter {

*
     *  * 3. JWT 필터에서 access_token 검증 및 refresh_token 갱신
     *      * 로그인 후 access_token을 헤더에 저장하고, refresh_token을 쿠키에 저장한 뒤,
     *      * 클라이언트에서 요청 시 access_token을 사용하고 만료되었을 때 refresh_token을 사용하여 새 access_token을 발급할 수 있습니다.
     *



    private final JwtUtil jwtUtil;

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        String accessToken = getAccessTokenFromHeader(request);

        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtUtil.getSecretKey())
                    .parseClaimsJws(accessToken)
                    .getBody();
            String username = claims.getSubject();
            // 인증된 사용자 객체를 반환
            return Optional.of(new UsernamePasswordAuthenticationToken(username, null, null));
        }

        return Optional.empty();
    }

    private String getAccessTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // "Bearer " 제거
        }
        return null;
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
*/
