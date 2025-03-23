package com.mung.mungtique.reservation.adaptor.out.web;


import com.mung.mungtique.reservation.application.port.out.web.MungShopApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class MungShopApiAdaptor implements MungShopApiPort {

    private final MungShopApi mungShopApi;

    @Override
    public boolean lockAndCheckAvailability(Long mungShopId, LocalDate reservationDate, String reservationTime) {
        return mungShopApi.lockAndCheckAvailability(mungShopId, reservationDate, reservationTime);
    }
}
