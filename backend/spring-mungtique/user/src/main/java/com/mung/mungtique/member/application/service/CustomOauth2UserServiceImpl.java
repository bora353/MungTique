package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.adaptor.in.web.dto.CustomOAuth2User;
import com.mung.mungtique.member.adaptor.in.web.dto.NaverRes;
import com.mung.mungtique.member.adaptor.in.web.dto.OAuth2Res;
import com.mung.mungtique.member.adaptor.in.web.dto.UserDTO;
import com.mung.mungtique.member.application.port.out.UserKakaoRepoPort;
import com.mung.mungtique.member.domain.Authority;
import com.mung.mungtique.member.domain.UserKakao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserKakaoRepoPort userKakaoRepoPort;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Res oAuth2Res = null;

        if (registrationId.equals("naver")) {
            oAuth2Res = new NaverRes(oAuth2User.getAttributes());

        } else {
            return null;
        }

        // 리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만들기
        String username = oAuth2Res.getProvider() + " " + oAuth2Res.getProviderId();

        // DB
        UserKakao existData = userKakaoRepoPort.findByUsername(username);

        if (existData == null) {
            UserKakao userKakao = UserKakao.builder()
                    .username(username)
                    .email(oAuth2Res.getEmail())
                    .name(oAuth2Res.getName())
                    .role(Authority.ROLE_USER)
                    .build();

            userKakaoRepoPort.save(userKakao);

            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .name(oAuth2Res.getName())
                    .role("ROLE_USER")
                    .build();

            // 꼭 정해진 타입으로 반환 필요
            return new CustomOAuth2User(userDTO);

        } else {
            UserKakao updateUser = UserKakao.builder()
                    .username(existData.getUsername())
                    .email(oAuth2Res.getEmail())
                    .name(oAuth2Res.getName())
                    .role(Authority.ROLE_USER)
                    .build();

            userKakaoRepoPort.save(updateUser);

            UserDTO userDTO = UserDTO.builder()
                    .username(updateUser.getUsername())
                    .name(updateUser.getName())
                    .role(updateUser.getRole())
                    .build();

            return new CustomOAuth2User(userDTO);
        }
    }
}
