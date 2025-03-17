package com.mung.mungtique.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PENDING("예약 완료. 결제 대기 중"),
    SUCCESS("결제 완료됨"),
    FAILED("결제 취소됨"),
    REFUNDED("환불됨");

    private final String text;
}
