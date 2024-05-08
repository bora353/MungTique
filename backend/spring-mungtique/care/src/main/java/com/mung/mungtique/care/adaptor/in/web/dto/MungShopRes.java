package com.mung.mungtique.care.adaptor.in.web.dto;

import lombok.Builder;

@Builder
public record MungShopRes (String storeName,String storeAddress, String breeds, String businessHours, String closingDays, String latitude, String longitude) {
}
