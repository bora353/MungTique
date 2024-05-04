package com.mung.mungtique.member.adaptor.in.web.dto;

import com.mung.mungtique.member.domain.Authority;
import lombok.Builder;

@Builder
public record UserDTO (Authority role, String name, String username) {
}
