package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.Token;

public interface TokenRepoPort {

    void save(Token token);

    Boolean existByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
