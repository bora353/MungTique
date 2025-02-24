package com.mung.mungtique.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {

    CUT("커트"),
    WASH("샴푸"),
    FULL("전체 서비스");

    private final String text;
}
