package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.ControllerTestSupport;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ReissueControllerTest extends ControllerTestSupport {

    @Value("${spring.jwt.refresh-expiration}")
    private String REFRESH_TOKEN_EXPIRATION_TIME;

    @Test
    @DisplayName("Access 토큰 만료 시 Refresh 토큰으로 재발급 성공")
    public void reissueSuccess() throws Exception {
        // Given
        String refreshToken = "mock-refresh-token";
        String newAccessToken = "mock-access-token";
        String newRefreshToken = "mock-new-refresh-token";

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", newAccessToken);
        tokens.put("refresh", newRefreshToken);

        when(tokenService.reissueToken(request)).thenReturn(tokens);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/reissue")
                        .header("Authorization", "Bearer " + refreshToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer " + newAccessToken))
                .andExpect(cookie().exists("refresh"))
                .andExpect(cookie().value("refresh", newRefreshToken));
    }

    @Test
    @DisplayName("Refresh 토큰이 잘못된 경우 재발급 실패")
    public void reissueFailure() throws Exception {
        // Given
        String invalidRefreshToken = "invalid-refresh-token";

        when(tokenService.reissueToken(any(HttpServletRequest.class)))
                .thenThrow(new IllegalArgumentException("Invalid refresh token"));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/reissue")
                        .header("Authorization", "Bearer " + invalidRefreshToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid refresh token"));
    }
}