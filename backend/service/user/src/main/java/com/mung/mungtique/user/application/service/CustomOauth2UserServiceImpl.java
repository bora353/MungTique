package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.adaptor.in.web.dto.*;
import com.mung.mungtique.user.application.port.in.CustomOauth2UserService;
import com.mung.mungtique.user.application.port.out.UserRepoPort;
import com.mung.mungtique.user.domain.Authority;
import com.mung.mungtique.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomOauth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOauth2UserService {

    private final UserRepoPort userRepoPort;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User: {}", oAuth2User);

        // OAuth2 제공자별 데이터 파싱
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Res oAuth2Res = switch (provider) {
            case "naver" -> new NaverRes(oAuth2User.getAttributes());
            case "kakao" -> new KakaoRes(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        };

        String providerId = oAuth2Res.getProviderId();

        // 기존 사용자 조회 또는 신규 저장
        UserEntity user = userRepoPort.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> createNewOAuth2User(provider, providerId, oAuth2Res));

        user.setLastLoginAt();
        userRepoPort.save(user);

        Oauth2UserDTO oauth2UserDTO = Oauth2UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .userId(user.getId())
                .build();

        return new CustomOAuth2User(oauth2UserDTO);
    }

    private UserEntity createNewOAuth2User(String provider, String providerId, OAuth2Res oAuth2Res) {
        return UserEntity.builder()
                .provider(provider)
                .providerId(providerId)
                .email(oAuth2Res.getEmail())
                .username(oAuth2Res.getName())
                .role(Authority.ROLE_USER)
                .build();
    }
}
