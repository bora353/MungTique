package com.mung.mungtique.user.adaptor.in.web.dto;

import lombok.Builder;

@Builder
public record JoinRes ( // TODO : validation 넣어야함
        Long id,
        String username,
        String password,
        String email,
        String phone
) {
}
