package com.mung.mungtique.member.adaptor.in.web;

import com.mung.mungtique.member.application.port.in.RefreshTokenService;
import com.mung.mungtique.member.infrastructure.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReissueController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String newAccessToken  = refreshTokenService.reissueToken(request);

        if (newAccessToken.startsWith("refresh token")) {
            return new ResponseEntity<>(newAccessToken, HttpStatus.BAD_REQUEST);
        }

        // response
        response.setHeader("access", newAccessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
