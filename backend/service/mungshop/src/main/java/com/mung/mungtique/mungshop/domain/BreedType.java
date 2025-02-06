package com.mung.mungtique.mungshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BreedType {

    GOLDEN_RETRIEVER("골든 리트리버"),
    POODLE("푸들"),
    CHIHUAHUA("치와와"),
    FRENCH_BULLDOG("프렌치 불독"),
    POMERANIAN("포메라니안"),
    SIBERIAN_HUSKY("시베리안 허스키"),
    MALTESE("말티즈"),
    ALL("모든 견종");

    private final String text;
}
