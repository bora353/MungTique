package com.mung.mungtique.user.application.port.out;

public interface RedisPort {
    void saveVerificationCodeFor3Minutes(String mail, int verificationCode);
    String findVerificationCode(String mail);
}
