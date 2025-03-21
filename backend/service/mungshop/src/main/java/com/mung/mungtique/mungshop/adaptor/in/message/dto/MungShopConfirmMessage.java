package com.mung.mungtique.mungshop.adaptor.in.message.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MungShopConfirmMessage(
        Long mungShopId,
        LocalDate reservationDate,
        String reservationTime
) {
}
