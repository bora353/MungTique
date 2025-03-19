package com.mung.mungtique.payment.application.service.mapper;

import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentReq;
import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentRes;
import com.mung.mungtique.payment.adaptor.out.message.dto.PaymentSuccessMessage;
import com.mung.mungtique.payment.domain.Payment;
import com.mung.mungtique.payment.domain.PaymentMethod;
import com.mung.mungtique.payment.domain.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMapperTest {

    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    public void toPaymentRes() {
        // Given
        Payment cardPayment = Payment.builder()
                .reservationId(1L)
                .userId(999L)
                .amount(30000)
                .paymentMethod(PaymentMethod.CARD)
                .cardNumber("123456780000")
                .cardExpiry("12/34")
                .cardCVC("353")
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        // When
        PaymentRes paymentRes = paymentMapper.toPaymentRes(cardPayment);

        // Then
        assertThat(paymentRes).isNotNull();
        assertThat(paymentRes.amount()).isEqualTo(cardPayment.getAmount());
        assertThat(paymentRes.paymentMethod()).isEqualTo(cardPayment.getPaymentMethod());
        assertThat(paymentRes.cardNumber()).isEqualTo(cardPayment.getCardNumber());
    }

    @Test
    public void toPayment() {
        // Given
        PaymentReq cardReq = PaymentReq.builder()
                .reservationId(1L)
                .userId(1L)
                .amount(30000)
                .paymentMethod(PaymentMethod.CARD)
                .cardNumber("123456780000")
                .cardExpiry("12/34")
                .cardCVC("353")
                .build();

        // When
        Payment payment = paymentMapper.toPayment(cardReq);

        // Then
        assertThat(payment).isNotNull();
        assertThat(payment.getReservationId()).isEqualTo(cardReq.reservationId());
        assertThat(payment.getUserId()).isEqualTo(cardReq.userId());
        assertThat(payment.getAmount()).isEqualTo(cardReq.amount());
        assertThat(payment.getPaymentMethod()).isEqualTo(PaymentMethod.CARD);
        assertThat(payment.getCardNumber()).isEqualTo(cardReq.cardNumber());
        assertThat(payment.getCardExpiry()).isEqualTo(cardReq.cardExpiry());
        assertThat(payment.getCardCVC()).isEqualTo(cardReq.cardCVC());
    }

    @Test
    public void toPaymentSuccessMessage() {
        // Given
        Payment cardPayment = Payment.builder()
                .reservationId(1L)
                .userId(999L)
                .amount(30000)
                .paymentMethod(PaymentMethod.CARD)
                .cardNumber("123456780000")
                .cardExpiry("12/34")
                .cardCVC("353")
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        // When
        PaymentSuccessMessage successMessage = paymentMapper.toPaymentSuccessMessage(cardPayment);

        // Then
        assertThat(successMessage).isNotNull();
        assertThat(successMessage.paymentId()).isEqualTo(cardPayment.getPaymentId());
        assertThat(successMessage.reservationId()).isEqualTo(cardPayment.getReservationId());
    }
}