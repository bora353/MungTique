package com.mung.mungtique.reservation.adaptor.out.web;


import com.mung.mungtique.reservation.application.port.out.web.MungShopApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MungShopApiAdaptor implements MungShopApiPort {

    private final MungShopApi mungShopApi;

    @Override
    public boolean checkAvailableTime(Long mungShopId, String reservationTime) {
        return mungShopApi.checkAvailableTime(mungShopId, reservationTime);
    }
}
