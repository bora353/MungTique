package com.mung.mungtique.reservation.adaptor.out.persistence;

import com.mung.mungtique.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
}
