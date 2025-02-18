package com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation;

import com.mung.mungtique.mungshop.domain.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MungShopReservationRes(Long mungShopReservationId, Long mungShopId,
                                     LocalDateTime reservationDateTime, ReservationStatus status) {
}
