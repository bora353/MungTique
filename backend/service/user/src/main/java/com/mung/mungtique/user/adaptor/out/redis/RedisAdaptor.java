package com.mung.mungtique.user.adaptor.out.redis;

import com.mung.mungtique.user.application.port.out.RedisPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisAdaptor implements RedisPort {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveVerificationCodeFor3Minutes(String mail, int verificationCode) {
        String redisKey = "user-service:" + mail + ":" + verificationCode;
        redisTemplate.opsForValue().set(redisKey, String.valueOf(verificationCode), 3, TimeUnit.MINUTES);
    }

    @Override
    public String findVerificationCode(String mail) {
        String redisKey = "user-service:" + mail;
        Object code = redisTemplate.opsForValue().get(redisKey);
        return code != null ? code.toString() : null;
    }
}
