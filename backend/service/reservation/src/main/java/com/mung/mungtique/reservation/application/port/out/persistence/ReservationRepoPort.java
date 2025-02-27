package com.mung.mungtique.reservation.application.port.out.persistence;


import com.mung.mungtique.reservation.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepoPort {
    Reservation save(Reservation reservation);
    List<Reservation> findByUserId(Long userId);
    Optional<Reservation> findById(Long reservationId);
}
