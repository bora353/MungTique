package com.mung.mungtique.care.adaptor.in.web.dto;

import lombok.Builder;

@Builder
public record MungJoinReq (String mungName, String breed, int weight, int age, String gender, String fixed, Long userId) {
}
