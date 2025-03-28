package com.mung.mungtique.mungshop.adaptor.in.initializer;

import com.mung.mungtique.mungshop.application.port.in.MungShopReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationDataInitializer implements ApplicationRunner {

    private final MungShopReservationService reservationService;

    @Override
    public void run(ApplicationArguments args) {
        reservationService.initializeReservationSlots();
    }
}
