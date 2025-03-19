package com.mung.mungtique.payment.application.service;

import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentReq;
import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentRes;
import com.mung.mungtique.payment.adaptor.out.message.dto.PaymentSuccessMessage;
import com.mung.mungtique.payment.adaptor.out.persistence.PaymentRepo;
import com.mung.mungtique.payment.adaptor.out.persistence.PaymentRepoAdaptor;
import com.mung.mungtique.payment.application.port.out.message.ReservationEventPort;
import com.mung.mungtique.payment.application.service.mapper.PaymentMapperImpl;
import com.mung.mungtique.payment.domain.Payment;
import com.mung.mungtique.payment.domain.PaymentMethod;
import com.mung.mungtique.payment.domain.PaymentStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceImplTest {

    @Autowired
    private PaymentRepoAdaptor paymentRepoPort;
    @Autowired
    private PaymentRepo paymentRepo;
    @MockBean
    private ReservationEventPort reservationEventPort;
    @Autowired
    private PaymentMapperImpl paymentMapper;
    @Autowired
    private PaymentServiceImpl paymentService;

    @AfterEach
    void tearDown() {
        paymentRepo.deleteAllInBatch();
    }

    @DisplayName("카드 결제 요청 처리한다.")
    @Test
    void processPaymentByCard() {
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

        PaymentSuccessMessage successMessage = new PaymentSuccessMessage(1L, 1L);

        doNothing().when(reservationEventPort).sendPaymentSuccessEvent(successMessage);

        // when
        long paymentId = paymentService.processPayment(cardReq);

        // then
        Assertions.assertThat(paymentId).isEqualTo(1L);
    }

    @DisplayName("paymentId로 payment 정보 가져온다.")
    @Test
    void getPayment() {
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
                .reservationId(2L)
                .userId(999L)
                .amount(40000)
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .accountHolder("이보라")
                .bankName("신한은행")
                .accountNumber("111222333333")
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepoPort.save(bankPayment);

        // when
        PaymentRes payment = paymentService.getPayment(savedPayment.getPaymentId());

        // then
        Assertions.assertThat(payment)
                .extracting("amount", "paymentMethod")
                .containsExactly(40000, PaymentMethod.BANK_TRANSFER);
    }
}