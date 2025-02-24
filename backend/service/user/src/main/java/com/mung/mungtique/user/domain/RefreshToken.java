package com.mung.mungtique.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@RedisHash(value = "refreshToken")
@AllArgsConstructor
@Getter
public class RefreshToken {

    @Id // 어노테이션 주의
    private String email;

    private String refreshToken;

    @TimeToLive
    private long expiration;
}
