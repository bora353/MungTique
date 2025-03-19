package com.mung.mungtique.payment.adaptor.in.web;

import com.mung.mungtique.payment.ControllerTestSupport;
import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentReq;
import com.mung.mungtique.payment.domain.PaymentMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerTest extends ControllerTestSupport {

    @DisplayName("카드 결제 요청 처리한다.")
    @Test
    void processPaymentByCard() throws Exception {
        // given
        PaymentReq cardReq = PaymentReq.builder()
                                    .reservationId(1L)
                                    .userId(1L)
                                    .amount(30000)
                                    .paymentMethod(PaymentMethod.CARD)
                                    .cardNumber("123456780000")
                                    .cardExpiry("12/34")
                                    .cardCVC("353")
                                    .build();

        when(paymentService.processPayment(cardReq)).thenReturn(1L);

        // when // then
        mockMvc.perform(
                post("/api/v1/payments")
                        .content(objectMapper.writeValueAsString(cardReq))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

    @DisplayName("계좌 결제 요청 처리한다.")
    @Test
    void processPaymentByBankTransfer() throws Exception {
        // given
        PaymentReq cardReq = PaymentReq.builder()
                .reservationId(1L)
                .userId(1L)
                .amount(30000)
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .accountHolder("이보라")
                .bankName("신한은행")
                .accountNumber("111222333333")
                .build();

        when(paymentService.processPayment(cardReq)).thenReturn(1L);

        // when // then
        mockMvc.perform(
                        post("/api/v1/payments")
                                .content(objectMapper.writeValueAsString(cardReq))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }
}