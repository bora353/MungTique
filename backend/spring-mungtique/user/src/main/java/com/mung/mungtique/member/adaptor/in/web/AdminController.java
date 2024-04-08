package com.mung.mungtique.member.adaptor.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String adminPage(){
        return "Admin Controller";
    }
}
