package com.mung.mungtique.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {

    WAITING_FOR_PAYMENT("결제 대기 중. 결제 실패"),
    PAID("결제 완료됨"),
    CANCELED("예약 취소됨");

    private final String text;

    /**
     * 참고 결제서비스의 상태는
     * PENDING, SUCCESS, FAILED, REFUNDED
     */
}
