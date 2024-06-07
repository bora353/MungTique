package com.mung.mungtique.care.adaptor.in.web.dto.mungshop;

import lombok.Builder;

@Builder
public record MungShopRes (Long id, String storeName,String storeAddress, String breeds, String businessHours, String closingDays, String latitude, String longitude, String filePath) {
}
