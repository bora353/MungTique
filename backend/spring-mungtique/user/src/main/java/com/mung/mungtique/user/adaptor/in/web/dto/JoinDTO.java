package com.mung.mungtique.user.adaptor.in.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinDTO { // TODO : record로 변경?

    private String username;
    private String password;
    private String passwordCheck;

    private String email;
    private String phone;

}
