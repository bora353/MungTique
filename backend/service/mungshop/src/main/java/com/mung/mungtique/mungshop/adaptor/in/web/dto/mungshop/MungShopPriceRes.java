package com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop;

import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.ServiceType;
import lombok.Builder;

@Builder
public record MungShopPriceRes(Long mungShopPriceId, BreedType breedType, ServiceType serviceType, int price) {
}
