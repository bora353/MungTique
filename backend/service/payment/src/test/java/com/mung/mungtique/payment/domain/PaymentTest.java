package com.mung.mungtique.payment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    @Test
    void completePayment() {
        // Given
        Payment payment = Payment.builder()
                .paymentId(1L)
                .reservationId(100L)
                .userId(200L)
                .amount(30000)
                .paymentMethod(PaymentMethod.CARD)
                .cardNumber("1234567890123456")
                .cardExpiry("12/25")
                .cardCVC("123")
                .build();

        // When
        payment.completePayment();

        // Then
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.SUCCESS);
    }

    @Test
    void processRefund() {
        // Given
        Payment payment = Payment.builder()
                .paymentId(1L)
                .reservationId(100L)
                .userId(200L)
                .amount(30000)
                .paymentMethod(PaymentMethod.CARD)
                .cardNumber("1234567890123456")
                .cardExpiry("12/25")
                .cardCVC("123")
                .build();
        payment.completePayment();

        // When
        payment.processRefund();

        // Then
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.REFUNDED);
        assertThat(payment.getIsDeleted()).isTrue();
    }
}