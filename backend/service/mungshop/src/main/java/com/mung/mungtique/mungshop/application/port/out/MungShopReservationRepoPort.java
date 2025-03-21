package com.mung.mungtique.mungshop.application.port.out;

import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.MungShopReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MungShopReservationRepoPort {
    List<MungShopReservation> findByMungShopIdAndStatus(Long mungShopId, MungShopReservationStatus mungShopReservationStatus);
    int updateStatusIfAvailable(Long mungShopId, String reservationTime, MungShopReservationStatus oldStatus, MungShopReservationStatus newStatus);
    Optional<MungShopReservation> findById(Long mungShopId);
    List<MungShopReservation> findReservationsWithStatusUpdatedBefore(MungShopReservationStatus mungShopReservationStatus, LocalDateTime expirationTime);
    MungShopReservation save(MungShopReservation reservation);
}
