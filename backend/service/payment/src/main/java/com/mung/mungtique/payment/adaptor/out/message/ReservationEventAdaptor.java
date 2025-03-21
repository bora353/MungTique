package com.mung.mungtique.payment.adaptor.out.message;

import com.mung.mungtique.payment.adaptor.out.message.dto.PaymentSuccessMessage;
import com.mung.mungtique.payment.application.port.out.message.ReservationEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationEventAdaptor implements ReservationEventPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendPaymentSuccessEvent(PaymentSuccessMessage event) {

        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send("payment-reservation-success-topic", event);

        future.whenComplete((result, failure) -> {
            if (result != null) {
                log.info("예약 시스템에 결제 완료 이벤트 전송 성공: {}",
                        result.getProducerRecord().value());
            } else if (failure != null) {
                log.error("예약 시스템에 결제 완료 이벤트 전송 실패. 오류 메시지: {}", failure.getMessage(), failure);
            }
        });
    }
}
