package com.mung.mungtique.mungshop.application.port.out;

import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.ReservationStatus;

import java.util.List;

public interface MungShopReservationRepoPort {
    List<MungShopReservation> findByMungShopIdAndStatus(Long mungShopId, ReservationStatus reservationStatus);
}
