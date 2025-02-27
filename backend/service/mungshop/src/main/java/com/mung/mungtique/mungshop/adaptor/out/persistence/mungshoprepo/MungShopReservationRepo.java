package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.MungShopReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MungShopReservationRepo extends JpaRepository<MungShopReservation, Long> {
    List<MungShopReservation> findByMungShopIdAndStatus(Long mungShopId, MungShopReservationStatus status);
    Boolean existsByMungShopIdAndReservationTimeAndStatus(Long mungShopId, String reservationTime, MungShopReservationStatus status);
}
