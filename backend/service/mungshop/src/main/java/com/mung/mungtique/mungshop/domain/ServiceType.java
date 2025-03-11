package com.mung.mungtique.mungshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceType {

    CUT("커트"),
    WASH("목욕"),
    FULL("전체 서비스");

    private final String text;
}
