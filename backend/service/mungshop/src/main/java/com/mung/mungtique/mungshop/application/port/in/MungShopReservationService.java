package com.mung.mungtique.mungshop.application.port.in;

import com.mung.mungtique.mungshop.adaptor.in.message.dto.MungShopConfirmMessage;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation.MungShopReservationRes;
import com.mung.mungtique.mungshop.domain.MungShopReservation;

import java.util.List;

public interface MungShopReservationService {
    List<MungShopReservationRes> getAvailableReservationInfo(Long mungShopId);
    Boolean lockAndCheckAvailability(Long mungShopId, String reservationTime);
    MungShopReservation confirmReservation(MungShopConfirmMessage message);
    int cancelExpiredReservations();
    void initializeReservationSlots();
}
