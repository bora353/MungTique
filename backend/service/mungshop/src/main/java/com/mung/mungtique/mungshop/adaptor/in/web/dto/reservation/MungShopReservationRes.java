package com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation;

import com.mung.mungtique.mungshop.domain.ReservationStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MungShopReservationRes(Long mungShopReservationId, Long mungShopId,
                                     LocalDate reservationDate, String reservationTime,
                                     ReservationStatus status) {
}
