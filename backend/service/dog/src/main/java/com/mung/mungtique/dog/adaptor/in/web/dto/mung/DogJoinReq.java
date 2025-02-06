package com.mung.mungtique.dog.adaptor.in.web.dto.mung;

import com.mung.mungtique.dog.domain.BreedType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record DogJoinReq(
        @NotBlank(message = "강아지 이름은 필수입니다.") String mungName,
        @NotNull(message = "견종은 필수입니다.") BreedType breedType,
        @Min(value = 0, message = "체중은 0 이상이어야 합니다.") int weight,
        @Min(value = 0, message = "나이는 0 이상이어야 합니다.") int age,
        @NotBlank(message = "성별은 필수입니다.") String gender,
        @NotBlank(message = "중성화 여부는 필수입니다.") String fixed,
        @NotNull(message = "사용자 ID는 필수입니다.") Long userId
) {
}