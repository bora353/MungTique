package com.mung.mungtique.care.adaptor.in.web.dto.mung;

import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.care.domain.BreedType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MungRes {
    private Long myMungId;
    private String mungName;
    private BreedType breedType;
    private int weight;
    private int age;
    private String gender;
    private String fixed;
    private Long userId;
    private ImageUploadRes image;
}
