package com.mung.mungtique.mungshop.adaptor.in.scheduler;


import com.mung.mungtique.mungshop.application.port.in.MungShopReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCleanupScheduler {

    private final MungShopReservationService mungShopReservationService;

    @Scheduled(fixedRate = 600000) // 10분마다 실행
    public void cancelExpiredReservations() {
        int expiredCount = mungShopReservationService.cancelExpiredReservations();
        log.info("Expired reservations count: {}", expiredCount);
    }
}
