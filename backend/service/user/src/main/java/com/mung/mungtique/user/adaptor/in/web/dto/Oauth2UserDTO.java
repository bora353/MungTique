package com.mung.mungtique.user.adaptor.in.web.dto;

import com.mung.mungtique.user.domain.Authority;
import lombok.Builder;

@Builder
public record Oauth2UserDTO(Authority role, String email, String username, Long userId) {
}
