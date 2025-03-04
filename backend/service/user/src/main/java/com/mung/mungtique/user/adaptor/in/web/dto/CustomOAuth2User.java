package com.mung.mungtique.user.adaptor.in.web.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final Oauth2UserDTO oauth2UserDTO;

    @Override
    public Map<String, Object> getAttributes() {
        // 형식이 달라 사용하지 않음
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return oauth2UserDTO.role().toString();
            }
        });
        return collection;
    }

    @Override
    public String getName() {
        return oauth2UserDTO.email();
    }

    public Long getUserId() {
        return oauth2UserDTO.userId();
    }

}
