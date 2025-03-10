package com.mung.mungtique.reservation.application.port.in;

import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;

import java.util.List;

public interface ReservationService {
    Long create(ReservationReq reservationReq);
    List<ReservationRes> getReservationsByUser(Long userId);
    Boolean cancelReservation(Long reservationId);
}
