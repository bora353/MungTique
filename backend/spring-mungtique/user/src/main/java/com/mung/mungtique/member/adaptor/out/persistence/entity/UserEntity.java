package com.mung.mungtique.member.adaptor.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String username;
    @Column(length = 20)
    private String password;

    @Column(unique = true, length = 50)
    private String email;
    @Column(length = 20)
    private String phone;

    private String role;

}
