package com.mung.mungtique.care.adaptor.in.web.dto.mungshop;

import lombok.Builder;

@Builder
public record MungShopLikeReq(Long mungShopId, Long userId) {
}
