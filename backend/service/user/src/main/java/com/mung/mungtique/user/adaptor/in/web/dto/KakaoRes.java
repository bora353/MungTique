package com.mung.mungtique.user.adaptor.in.web.dto;

import java.util.Map;
import java.util.Optional;

public class KakaoRes implements OAuth2Res {

    private final Map<String, Object> attributes;
    private final Map<String, Object> kakaoAccountAttributes;
    private final Map<String, Object> propertyAttributes;

    public KakaoRes(Map<String, Object> attributes) {

        this.attributes = attributes;
        this.kakaoAccountAttributes = Optional.ofNullable((Map<String, Object>) attributes.get("kakao_account")).orElse(Map.of());
        this.propertyAttributes = Optional.ofNullable((Map<String, Object>) attributes.get("properties")).orElse(Map.of());
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return "kakao_email_" + getProviderId() + "@mungtique.com"; // 카카오는 비즈 회원만 이메일 제공함
        //return kakaoAccountAttributes.get("email").toString();
    }

    @Override
    public String getName() {
        return propertyAttributes.get("nickname").toString();
    }
}
