package com.mung.mungtique.user.adaptor.in.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public record JoinReq (
        String username,
        String password,
        String passwordCheck,
        String email,
        String phone
) {
}
