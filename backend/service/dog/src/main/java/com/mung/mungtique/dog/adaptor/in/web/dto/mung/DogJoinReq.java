package com.mung.mungtique.dog.adaptor.in.web.dto.mung;

import com.mung.mungtique.dog.domain.BreedType;
import lombok.Builder;

@Builder
public record DogJoinReq(String mungName, BreedType breedType, int weight, int age, String gender, String fixed, Long userId) {
}
