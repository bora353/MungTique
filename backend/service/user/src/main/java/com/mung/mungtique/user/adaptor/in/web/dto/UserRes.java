package com.mung.mungtique.user.adaptor.in.web.dto;

import lombok.Builder;

@Builder
public record UserRes(String username, String email, String phone) {
}
