package com.mung.mungtique.reservation.application.service.mapper;


import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;
import com.mung.mungtique.reservation.adaptor.out.message.dto.MungShopConfirmMessage;
import com.mung.mungtique.reservation.domain.Reservation;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.util.List;


@Mapper (componentModel = "spring")
public interface ReservationMapper {

    Reservation toReservation(ReservationReq reservationReq);
    List<ReservationRes> toReservationResList(List<Reservation> reservations);
    ReservationRes toReservationRes(Reservation reservations);
    MungShopConfirmMessage toMungShopConfirmMessage(Long mungShopId, LocalDate reservationDate, String reservationTime);
}
