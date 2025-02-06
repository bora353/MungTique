package com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop;

import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.ClosingDayType;
import lombok.Builder;

@Builder
public record MungShopRes (Long mungShopId, String storeName, String storeAddress, BreedType breedType, String businessHours, ClosingDayType closingDays, String latitude, String longitude, String filePath) {
}
