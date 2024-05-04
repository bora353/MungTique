package com.mung.mungtique.member.application.port.out;

import com.mung.mungtique.member.domain.Token;

public interface TokenRepoPort {

    void save(Token token);

    Boolean existByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
