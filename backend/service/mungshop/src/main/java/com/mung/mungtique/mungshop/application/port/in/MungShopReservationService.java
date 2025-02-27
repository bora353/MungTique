package com.mung.mungtique.mungshop.application.port.in;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation.MungShopReservationRes;

import java.util.List;

public interface MungShopReservationService {
    List<MungShopReservationRes> getAvailableReservationInfo(Long mungShopId);
    Boolean checkAvailableTime(Long mungShopId, String reservationTime);
}
