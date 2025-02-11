package com.mung.mungtique.dog.adaptor.in.web.dto.mung;


import com.mung.mungtique.dog.domain.BreedType;
import com.mung.mungtique.dog.domain.FixedStatus;
import com.mung.mungtique.dog.domain.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DogRes {
    private Long dogId;
    private String dogName;
    private BreedType breedType;
    private int weight;
    private int age;
    private Gender gender;
    private FixedStatus fixed;
    private Long userId;
    private String imageUrl;
}
