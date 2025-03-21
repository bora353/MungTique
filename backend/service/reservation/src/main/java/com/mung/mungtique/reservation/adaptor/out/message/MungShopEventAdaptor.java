package com.mung.mungtique.reservation.adaptor.out.message;

import com.mung.mungtique.reservation.adaptor.out.message.dto.MungShopConfirmMessage;
import com.mung.mungtique.reservation.application.port.out.message.MungShopEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class MungShopEventAdaptor implements MungShopEventPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendReservationConfirmToMungShop(MungShopConfirmMessage event) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send("payment-mungshop-success-topic", event);

        future.whenComplete((result, failure) -> {
            if (result != null) {
                log.info("뭉샵에 결제 완료 이벤트 전송 성공: {}",
                        result.getProducerRecord().value());
            } else if (failure != null) {
                log.error("뭉샵에 결제 완료 이벤트 전송 실패. 오류 메시지: {}", failure.getMessage(), failure);
            }
        });
    }
}
