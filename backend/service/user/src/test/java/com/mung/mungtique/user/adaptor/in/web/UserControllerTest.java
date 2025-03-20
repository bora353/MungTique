package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.ControllerTestSupport;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import com.mung.mungtique.user.adaptor.in.web.dto.UserRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends ControllerTestSupport {

    @DisplayName("user 회원가입한다.")
    @Test
    public void registerUser() throws Exception {
        // Given
        JoinReq joinReq = JoinReq.builder()
                .username("이보라")
                .password("123456")
                .passwordCheck("123456")
                .email("mungtique@gmail.com")
                .phone("010-1234-5678")
                .build();

        JoinRes joinRes = JoinRes.builder()
                .id(1L)
                .username("이보라")
                .password("123456")
                .email("mungtique@gmail.com")
                .phone("010-1234-5678")
                .build();

        when(userService.createUser(joinReq)).thenReturn(joinRes);

        // When // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/join")
                        .content(objectMapper.writeValueAsString(joinReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("이보라"))
                .andExpect(jsonPath("$.email").value("mungtique@gmail.com"));
    }

    @DisplayName("user 회원가입 시 Conflict 상태가 발생한다.")
    @Test
    public void registerUser_Conflict() throws Exception {
        // Given
        JoinReq joinReq = JoinReq.builder()
                .username("이보라")
                .password("123456")
                .passwordCheck("123456")
                .email("mungtique@gmail.com")
                .phone("010-1234-5678")
                .build();

        when(userService.createUser(joinReq)).thenThrow(new IllegalArgumentException());

        // When // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/join")
                        .content(objectMapper.writeValueAsString(joinReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @DisplayName("user 정보를 가져온다.")
    @Test
    public void getUserById() throws Exception {
        // Given
        String userId = "1L";
        UserRes userRes = UserRes.builder()
                .username("이보라")
                .email("mungtique@gmail.com")
                .phone("010-1234-5678")
                .build();

        when(userService.getUserInfo(userId)).thenReturn(userRes);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("이보라"))
                .andExpect(jsonPath("$.email").value("mungtique@gmail.com"));
    }
}
