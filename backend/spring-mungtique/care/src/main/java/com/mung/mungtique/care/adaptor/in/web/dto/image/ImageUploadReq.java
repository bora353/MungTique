package com.mung.mungtique.care.adaptor.in.web.dto.image;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ImageUploadReq(Long mungId, MultipartFile file) {
}
