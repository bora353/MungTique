package com.mung.mungtique.care.adaptor.in.web.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record ImageUploadReq(Long mungId, MultipartFile file) {
}
