package com.mung.mungtique.mungshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MungShopReservationStatus {
    AVAILABLE("예약 가능"),
    BOOKED("예약됨");

    private final String text;
}
