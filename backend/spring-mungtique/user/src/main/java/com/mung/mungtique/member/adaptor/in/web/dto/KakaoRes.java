package com.mung.mungtique.member.adaptor.in.web.dto;

import java.util.Map;

public class KakaoRes implements OAuth2Res {

    private final Map<String, Object> attributes;
    private final Map<String, Object> kakaoAccountAttributes;
    private final Map<String, Object> propertyAttributes;

    public KakaoRes(Map<String, Object> attributes) {

        this.attributes = attributes;
        this.kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");
        this.propertyAttributes = (Map<String, Object>) attributes.get("properties");
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
        return "kakao_email";
        //return kakaoAccountAttributes.get("email").toString();
    }

    @Override
    public String getName() {
        return propertyAttributes.get("nickname").toString();
    }
}
