package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.application.port.in.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class ReissueController {

    /**
     * 서버측 JWTFilter에서 Access 토큰의 만료로 인한 특정한 상태 코드(401)가 응답되면
     * 프론트측 Axios Interceptor와 같은 예외 핸들러에서 Access 토큰 재발급을 위한 Refresh을 서버측으로 전송!
     * 이때 서버에서는 Refresh 토큰을 받아 새로운 Access 토큰을 응답함

     * Refresh Rotate : Access 토큰 갱신 시 Refresh 토큰도 함께 갱신
     * 장점 : 로그인 지속시간 길어짐, 보안성 강화
     * 단, Rotate 되기 이전의 토큰을 가지고 서버측으로 가도 인증이 되기 때문에
     * 서버측에서 발급했던 Refresh들을 기억한 뒤 블랙리스트 처리를 진행하는 로직을 작성해야 함
    */

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> tokens = tokenService.reissueToken(request);
            response.setHeader("access", tokens.get("access"));
            response.addCookie(createCookie("refresh", tokens.get("refresh")));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);

        return cookie;
    }
}
