package com.mung.mungtique.mungshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MungShopReservationStatus {
    AVAILABLE("예약 가능"),
    PENDING_PAYMENT("예약은 되었으나 결제 대기 중"),
    BOOKED("예약됨. 결제까지 완료");

    private final String text;
}
