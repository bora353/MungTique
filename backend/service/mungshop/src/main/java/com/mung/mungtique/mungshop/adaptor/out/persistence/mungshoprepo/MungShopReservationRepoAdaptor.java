package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.application.port.out.MungShopReservationRepoPort;
import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.MungShopReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MungShopReservationRepoAdaptor implements MungShopReservationRepoPort {

    private final MungShopReservationRepo mungShopReservationRepo;

    @Override
    public List<MungShopReservation> findByMungShopIdAndStatus(Long mungShopId, MungShopReservationStatus mungShopReservationStatus) {
        return mungShopReservationRepo.findByMungShopIdAndStatus(mungShopId, mungShopReservationStatus);
    }

    @Override
    public Boolean existsByMungShopIdAndReservationTimeAndStatus(Long mungShopId, String reservationTime, MungShopReservationStatus status) {
        return mungShopReservationRepo.existsByMungShopIdAndReservationTimeAndStatus(mungShopId, reservationTime, status);
    }
}
