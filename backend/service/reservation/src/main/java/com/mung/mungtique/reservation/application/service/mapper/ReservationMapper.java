package com.mung.mungtique.reservation.application.service.mapper;


import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;
import com.mung.mungtique.reservation.domain.Reservation;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper (componentModel = "spring")
public interface ReservationMapper {

    Reservation toReservation(ReservationReq reservationReq);

    List<ReservationRes> toReservationRes(List<Reservation> reservations);
}
