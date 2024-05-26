package com.mung.mungtique.user.adaptor.in.web.dto;

import com.mung.mungtique.user.domain.Authority;
import lombok.Builder;

@Builder
public record UserDTO (Authority role, String name, String username) {
}
