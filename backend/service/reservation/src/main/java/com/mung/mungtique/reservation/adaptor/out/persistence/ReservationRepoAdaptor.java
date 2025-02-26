package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.application.port.out.MungShopReservationRepoPort;
import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MungShopReservationRepoAdaptor implements MungShopReservationRepoPort {

    private final MungShopReservationRepo mungShopReservationRepo;

    @Override
    public List<MungShopReservation> findByMungShopIdAndStatus(Long mungShopId, ReservationStatus reservationStatus) {
        return mungShopReservationRepo.findByMungShopIdAndStatus(mungShopId, reservationStatus);
    }
}
