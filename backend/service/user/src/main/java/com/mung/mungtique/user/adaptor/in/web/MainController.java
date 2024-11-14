package com.mung.mungtique.user.adaptor.in.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/user-info")
    public String mainPage(){

        // JWTFilter를 통과한 뒤 세션 확인
        // 세션에서 현재 인증된 사용자 이름
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 세션에서 현재 인증된 사용자 role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        return "Main Controller " + username + role;
    }

    @GetMapping("/welcome")
    public String welcomePage(){
        return "welcome to Mungtique User";
    }
}
