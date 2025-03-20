package com.mung.mungtique.user.adaptor.in.web;

import com.mung.mungtique.user.ControllerTestSupport;
import com.mung.mungtique.user.adaptor.in.web.dto.EmailReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmailControllerTest extends ControllerTestSupport {

    @DisplayName("이메일 중복 체크 후 인증 코드 발송")
    @Test
    public void sendMail() throws Exception {
        // Given
        EmailReq emailReq = new EmailReq("mungtique@gmail.com");

        when(emailService.isEmailDuplicate(emailReq.email())).thenReturn(false);
        when(emailService.sendEmailWithVerificationCode(emailReq.email())).thenReturn(123456);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/mail-send")
                        .content(objectMapper.writeValueAsString(emailReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("123456"));
    }

    @DisplayName("이메일 중복 시 Conflict 상태 코드 반환")
    @Test
    public void sendMail_Conflict() throws Exception {
        // Given
        EmailReq emailReq = new EmailReq("mungtique@gmail.com");

        when(emailService.isEmailDuplicate(emailReq.email())).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/mail-send")
                        .content(objectMapper.writeValueAsString(emailReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("duplicate email"));
    }

    @DisplayName("인증 코드 확인")
    @Test
    public void checkMail() throws Exception {
        // Given
        String email = "mungtique@gmail.com";
        String providedVerificationCode = "123456";

        when(emailService.checkMailVerificationCode(email, providedVerificationCode)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/mail-check")
                        .param("email", email)
                        .param("providedVerificationCode", providedVerificationCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @DisplayName("인증 코드 불일치 시 Conflict 상태 코드 반환")
    @Test
    public void checkMail_Conflict() throws Exception {
        // Given
        String email = "mungtique@gmail.com";
        String providedVerificationCode = "123456";

        when(emailService.checkMailVerificationCode(email, providedVerificationCode)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/mail-check")
                        .param("email", email)
                        .param("providedVerificationCode", providedVerificationCode))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("false"));
    }
}