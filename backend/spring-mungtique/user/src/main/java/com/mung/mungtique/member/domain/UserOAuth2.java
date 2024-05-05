package com.mung.mungtique.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_oauth2")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOAuth2 extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String username;

    @Column(length = 20)
    private String name;

    @Column(unique = true, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Authority role;
}
