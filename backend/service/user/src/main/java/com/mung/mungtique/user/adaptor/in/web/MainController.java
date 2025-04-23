package com.mung.mungtique.user.adaptor.in.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MainController {

    @GetMapping("/welcome")
    public String welcomePage(){
        return "welcome to Mungtique User";
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyEmail(@RequestHeader("X-User-email") String email ) {
        log.info("Received email from gateway: {}", email);
        return ResponseEntity.ok(email);
    }

    /**
     * 현재 게이트웨이 사용 중으로 사용 불가
     */
    @GetMapping("/user-info")
    public String mainPage(){
        // TODO :
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "인증되지 않은 사용자";
        }

        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            roles.append(authority.getAuthority()).append(" ");
        }

        return "Main Controller - 사용자: " + username + ", 권한: " + roles.toString().trim();
    }

}
