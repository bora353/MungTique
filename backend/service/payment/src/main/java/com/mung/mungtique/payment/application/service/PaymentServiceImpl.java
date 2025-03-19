package com.mung.mungtique.payment.application.service;

import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentReq;
import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentRes;
import com.mung.mungtique.payment.application.port.in.PaymentService;
import com.mung.mungtique.payment.application.port.out.message.ReservationEventPort;
import com.mung.mungtique.payment.application.port.out.persistence.PaymentRepoPort;
import com.mung.mungtique.payment.application.service.mapper.PaymentMapperImpl;
import com.mung.mungtique.payment.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepoPort paymentRepoPort;
    private final ReservationEventPort reservationEventPort;
    private final PaymentMapperImpl mapper;

    /** // TODO :
     * 결제하기전에 뭉샵에 시간 가능한지 카프카로 한번 체크하고
     * 결제완료되면 그 시간 이용 못하게 카프카 보내서 바꾸자
     */

    @Override
    @Transactional
    public long processPayment(PaymentReq paymentRequest) {
        Payment payment = mapper.toPayment(paymentRequest);

        long paymentId = switch (payment.getPaymentMethod()) {
            case CARD -> processCardPayment(payment);
            case BANK_TRANSFER -> processBankTransfer(payment);
            default -> throw new IllegalArgumentException("지원하지 않는 결제 방식입니다: " + payment.getPaymentMethod());
        };

        payment.completePayment();
        sendPaymentSuccessEvent(payment);

        return paymentId;
    }

    private void sendPaymentSuccessEvent(Payment payment) {
        reservationEventPort.sendPaymentSuccessEvent(mapper.toPaymentSuccessMessage(payment));
    }

    @Override
    public PaymentRes getPayment(Long paymentId) {
        Payment payment = paymentRepoPort.findById(paymentId).orElseThrow(() -> new IllegalStateException("예약번호로 예약정보를 찾을 수 없습니다."));
        return mapper.toPaymentRes(payment);
    }

    private long processCardPayment(Payment payment) {
        // 카드 결제 처리 로직 (예: PG사 API 호출)
        // 카드 번호, 유효기간, CVC 등을 사용
        payment = paymentRepoPort.save(payment);
        return payment.getPaymentId();
    }

    private long processBankTransfer(Payment payment) {
        // 계좌이체 처리 로직 (예: 은행 API 호출)
        // 계좌 정보, 은행명 등을 사용
        payment = paymentRepoPort.save(payment);
        return payment.getPaymentId();
    }
}
