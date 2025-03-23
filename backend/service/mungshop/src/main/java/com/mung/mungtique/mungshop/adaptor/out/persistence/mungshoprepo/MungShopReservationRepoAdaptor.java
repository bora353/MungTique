package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.application.port.out.MungShopReservationRepoPort;
import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.MungShopReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MungShopReservationRepoAdaptor implements MungShopReservationRepoPort {

    private final MungShopReservationRepo reservationRepo;

    @Override
    public List<MungShopReservation> findByMungShopIdAndStatus(Long mungShopId, MungShopReservationStatus mungShopReservationStatus) {
        return reservationRepo.findByMungShopIdAndStatus(mungShopId, mungShopReservationStatus);
    }

    @Override
    public int updateStatusIfAvailable(Long mungShopId, LocalDate reservationDate, String reservationTime, MungShopReservationStatus oldStatus, MungShopReservationStatus newStatus) {
        return reservationRepo.updateStatusIfAvailable(mungShopId, reservationDate, reservationTime, oldStatus, newStatus);
    }

    @Override
    public Optional<MungShopReservation> findById(Long mungShopId) {
        return reservationRepo.findById(mungShopId);
    }

    @Override
    public List<MungShopReservation> findReservationsWithStatusUpdatedBefore(MungShopReservationStatus status, LocalDateTime expirationTime) {
        return reservationRepo.findExpiredReservationsByStatus(status, expirationTime);
    }

    @Override
    public MungShopReservation save(MungShopReservation reservation) {
        return reservationRepo.save(reservation);
    }

    @Override
    public List<MungShopReservation> saveAll(List<MungShopReservation> reservations) {
        return reservationRepo.saveAll(reservations);
    }

    @Override
    public Optional<MungShopReservation> findByMungShopIdAndReservationDateAndReservationTime(Long mungShopId, LocalDate reservationDate, String reservationTime) {
        return reservationRepo.findByMungShopIdAndReservationDateAndReservationTime(mungShopId, reservationDate, reservationTime);
    }
}
