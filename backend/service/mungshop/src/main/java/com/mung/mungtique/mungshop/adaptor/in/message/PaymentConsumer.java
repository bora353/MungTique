package com.mung.mungtique.mungshop.adaptor.in.message;

import com.mung.mungtique.mungshop.adaptor.in.message.dto.MungShopConfirmMessage;
import com.mung.mungtique.mungshop.application.port.in.MungShopReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final MungShopReservationService mungShopReservationService;

    @KafkaListener(topics = "payment-mungshop-success-topic", containerFactory = "mungShopKafkaListenerContainerFactory")
    public void paymentSuccessListener(MungShopConfirmMessage message) {
        log.info("결제 완료 이벤트 수신: {}", message);

        mungShopReservationService.confirmReservation(message);
    }
}
