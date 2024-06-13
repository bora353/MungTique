package com.mung.mungtique.user.adaptor.in.web.dto;

import lombok.Builder;

@Builder
public record JoinRes (
        Long id,
        String username,
        String password,
        String email,
        String phone
) {
}
