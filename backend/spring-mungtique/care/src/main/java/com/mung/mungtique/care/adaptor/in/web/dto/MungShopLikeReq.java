package com.mung.mungtique.care.adaptor.in.web.dto;

import lombok.Builder;

@Builder
public record MungShopLikeReq(Long mungShopId, Long userId) {
}