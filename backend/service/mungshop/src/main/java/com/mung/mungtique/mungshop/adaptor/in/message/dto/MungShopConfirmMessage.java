package com.mung.mungtique.reservation.adaptor.out.message.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MungShopConfirmMessage(
        Long mungShopId,
        LocalDate reservationDate,
        String reservationTime
) {
}
