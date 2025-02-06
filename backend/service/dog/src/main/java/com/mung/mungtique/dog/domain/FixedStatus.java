package com.mung.mungtique.dog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FixedStatus {
    YES("중성화 O"),
    NO("중성화 X");

    private final String text;

}
