package com.mung.mungtique.payment.adaptor.out.persistence;

import com.mung.mungtique.payment.domain.Payment;
import com.mung.mungtique.payment.domain.PaymentMethod;
import com.mung.mungtique.payment.domain.PaymentStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
@ActiveProfiles("test")
@Import(PaymentRepoAdaptor.class)
class PaymentRepoAdaptorTest {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PaymentRepoAdaptor paymentRepoAdaptor;

    @DisplayName("userId로 payment 정보 가져온다.")
    @Test
    void findByUserId() {
        // given
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

        Payment bankPayment = Payment.builder()
                .reservationId(1L)
                .userId(999L)
                .amount(40000)
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .accountHolder("이보라")
                .bankName("신한은행")
                .accountNumber("111222333333")
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        paymentRepoAdaptor.save(cardPayment);
        paymentRepoAdaptor.save(bankPayment);

        // when
        List<Payment> payments = paymentRepoAdaptor.findByUserId(999L);

        // then
        Assertions.assertThat(payments).hasSize(2)
                .extracting("amount", "paymentMethod")
                .containsExactlyInAnyOrder(
                        tuple(30000, PaymentMethod.CARD),
                        tuple(40000, PaymentMethod.BANK_TRANSFER)
                );
    }

    @DisplayName("paymentId로 payment 정보 가져온다.")
    @Test
    void findById() {
        // given
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

        Payment bankPayment = Payment.builder()
                .reservationId(1L)
                .userId(999L)
                .amount(40000)
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .accountHolder("이보라")
                .bankName("신한은행")
                .accountNumber("111222333333")
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        paymentRepoAdaptor.save(cardPayment);
        paymentRepoAdaptor.save(bankPayment);

        // when
        Payment payment = paymentRepoAdaptor.findById(2L).orElseThrow();

        // then
        Assertions.assertThat(payment)
                .extracting("amount", "paymentMethod")
                .containsExactly(40000, PaymentMethod.BANK_TRANSFER);
    }
}