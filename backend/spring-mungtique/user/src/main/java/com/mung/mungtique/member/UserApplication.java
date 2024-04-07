package com.mung.mungtique.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);
        System.out.println("member world!");
    }
}