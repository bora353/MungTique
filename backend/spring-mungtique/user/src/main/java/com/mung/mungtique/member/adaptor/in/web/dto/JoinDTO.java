package com.mung.mungtique.member.adaptor.in.web.dto;

import lombok.Data;

@Data
public class JoinDTO { // TODO : record로 변경?

    private String username;
    private String password;
    private String passwordCheck;

    private String email;
    private String phone;

}
