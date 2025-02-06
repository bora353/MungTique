package com.mung.mungtique.user.adaptor.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public record EmailReq(
        @NotEmpty(message = "메일 주소는 필수입니다.")
        @Email(message = "유효한 이메일 주소를 입력하세요.")
        String email
) {}
