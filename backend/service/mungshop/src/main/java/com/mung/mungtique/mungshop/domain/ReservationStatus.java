package com.mung.mungtique.mungshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    NOT_RESERVED("예약되지 않음"),
    PENDING("대기중"),
    COMPLETE("완료"),
    CANCELED("취소");

    private final String text;
}
