package com.mung.mungtique.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_oauth2")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
