package com.mung.mungtique.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "mung_user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = true)
    @Setter
    private String password;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Authority role;

    @Column(nullable = true)
    private String provider; // 카카오, 네이버 등 구분

    @Column(nullable = true)
    private String providerId; // OAuth2 제공자의 사용자 ID

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginAt;

    public void setLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

    // TODO : 추후 IP, Location 정보 추가
}
