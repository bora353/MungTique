package com.mung.mungtique.dog.adaptor.in.web.dto.image;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ImageUploadReq(Long dogId, MultipartFile file) {
}
