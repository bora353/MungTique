package com.mung.mungtique.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CARD("카드 결제"),
    BANK_TRANSFER("계좌 이체");

    private final String description;
}
