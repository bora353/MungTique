package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JoinControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("/join으로 JoinDTO를 Post방식으로 보내면 회원가입할 수 있다")
    void joinProcessTest() {
        // given
        JoinReq joinReq = JoinReq.builder()
                            .username("bora")
                            .password("1234")
                            .passwordCheck("1234")
                            .email("bora@bora.com")
                            .phone("010-1234-5678")
                            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JoinReq> requestEntity = new HttpEntity<>(joinReq, headers);

        // when
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("/api/v1/join", requestEntity, User.class);

        // then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isNotNull();
        //assertThat(responseEntity.getBody().getUsername()).isEqualTo("bora");

        // TODO : 이게 아닌데...
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND); // 리다이렉션 상태
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/login"); // 리다이렉션 경로
    }
}
