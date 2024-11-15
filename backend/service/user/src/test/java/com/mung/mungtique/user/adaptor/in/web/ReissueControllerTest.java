package com.mung.mungtique.user.adaptor.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReissueControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testReissueEndpoint() {
        // Given
        String refreshToken = "sampleRefreshToken";

        // When
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/api/v1/reissue", refreshToken, Void.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/user/login");
    }
}
