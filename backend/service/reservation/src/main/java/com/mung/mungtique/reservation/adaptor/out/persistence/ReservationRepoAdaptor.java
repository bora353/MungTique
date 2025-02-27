package com.mung.mungtique.reservation.adaptor.out.persistence;

import com.mung.mungtique.reservation.application.port.out.persistence.ReservationRepoPort;
import com.mung.mungtique.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepoAdaptor implements ReservationRepoPort {

    private final ReservationRepo reservationRepo;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return reservationRepo.findByUserId(userId);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return reservationRepo.findById(reservationId);
    }
}
