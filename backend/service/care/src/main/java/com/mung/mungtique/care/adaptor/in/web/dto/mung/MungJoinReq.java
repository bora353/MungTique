package com.mung.mungtique.care.adaptor.in.web.dto.mung;

import com.mung.mungtique.care.domain.BreedType;
import lombok.Builder;

@Builder
public record MungJoinReq (String mungName, BreedType breedType, int weight, int age, String gender, String fixed, Long userId) {
}
