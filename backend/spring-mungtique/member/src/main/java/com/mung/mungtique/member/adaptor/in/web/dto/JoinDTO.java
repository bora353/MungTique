package com.mung.mungtique.member.adaptor.in.web.dto;

import lombok.Data;

@Data
public class JoinDTO {

    private String username;
    private String phone;
    private String email;
    private String password;
    private String passwordCheck;

}
