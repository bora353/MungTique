package com.mung.mungtique.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {

    ROLE_USER("일반"),
    ROLE_ADMIN("관리자");

    private final String text;
}
