package com.mung.mungtique.reservation.adaptor.in.message;

import com.mung.mungtique.reservation.adaptor.in.message.dto.PaymentSuccessMessage;
import com.mung.mungtique.reservation.application.port.in.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final ReservationService reservationService;

    @KafkaListener(topics = "payment-reservation-success-topic", containerFactory = "paymentKafkaListenerContainerFactory")
    public void paymentSuccessListener(PaymentSuccessMessage message) {
        log.info("예약서비스 - 결제 완료 이벤트 수신: {}", message);

        reservationService.updateReservationToPaid(message);
        reservationService.sendReservationConfirmToMungShop(message);
    }
}
