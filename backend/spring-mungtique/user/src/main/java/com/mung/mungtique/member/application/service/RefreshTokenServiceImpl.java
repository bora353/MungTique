package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.application.port.in.RefreshTokenService;
import com.mung.mungtique.member.infrastructure.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtUtil jwtUtil;

    @Override
    public String reissueToken(HttpServletRequest request) {

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            // response status code
            //return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
            return "refresh token null";
        }

        // expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            // response status code
            //return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
            return "refresh token expired";
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            // response status code
            //return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
            return "invalid refresh token";
        }

        String email = jwtUtil.getEmail(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccessToken = jwtUtil.createJwt("access", email, role, 600000L); // 10분
        return newAccessToken;
    }
}
