package com.mung.mungtique.care.adaptor.in.web.dto.mungshop;

import lombok.Builder;

@Builder
public record MungShopLikeRes(Long mungShopLikeId, MungShopRes mungShop, Long userId) {
}
