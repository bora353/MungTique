package com.mung.mungtique.care.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClosingDayType {

    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일"),
    NONE("휴무없음");

    private final String text;
}
