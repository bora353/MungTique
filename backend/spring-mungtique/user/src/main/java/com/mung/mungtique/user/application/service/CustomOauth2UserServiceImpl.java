package com.mung.mungtique.user.application.service;

import com.mung.mungtique.user.adaptor.in.web.dto.*;
import com.mung.mungtique.user.application.port.out.UserOAuth2RepoPort;
import com.mung.mungtique.user.domain.Authority;
import com.mung.mungtique.user.domain.UserOAuth2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserOAuth2RepoPort userOAuth2RepoPort;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Res oAuth2Res = null;

        if (registrationId.equals("naver")) {
            oAuth2Res = new NaverRes(oAuth2User.getAttributes());

        } else if (registrationId.equals("kakao")) {
            oAuth2Res = new KakaoRes(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만들기
        String username = oAuth2Res.getProvider() + " " + oAuth2Res.getProviderId();

        // DB
        UserOAuth2 existData = userOAuth2RepoPort.findByUsername(username);

        if (existData == null) {
            UserOAuth2 userOAuth2 = UserOAuth2.builder()
                    .username(username)
                    .email(oAuth2Res.getEmail())
                    .name(oAuth2Res.getName())
                    .role(Authority.ROLE_USER)
                    .build();

            userOAuth2RepoPort.save(userOAuth2);

            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .name(oAuth2Res.getName())
                    .role(Authority.ROLE_USER)
                    .build();

            // 꼭 정해진 타입으로 반환 필요
            return new CustomOAuth2User(userDTO);

        } else {
            UserOAuth2 updateUser = UserOAuth2.builder()
                    .username(existData.getUsername())
                    .email(oAuth2Res.getEmail())
                    .name(oAuth2Res.getName())
                    .role(Authority.ROLE_USER)
                    .build();

            userOAuth2RepoPort.save(updateUser);

            UserDTO userDTO = UserDTO.builder()
                    .username(updateUser.getUsername())
                    .name(updateUser.getName())
                    .role(updateUser.getRole())
                    .build();

            return new CustomOAuth2User(userDTO);
        }
    }
}
