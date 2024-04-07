package com.mung.mungtique.member.adaptor.in.web.dto;

import lombok.Data;

@Data
public class JoinDTO {

    private String username;
    private String password;
    private String passwordCheck;

    private String email;
    private String phone;

}
