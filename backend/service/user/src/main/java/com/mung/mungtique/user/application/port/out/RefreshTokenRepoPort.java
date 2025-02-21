package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.RefreshToken;

public interface RefreshTokenRepoPort {

    RefreshToken save(RefreshToken refreshToken);
    Boolean existByRefreshToken(String refreshToken);
    void deleteRefreshToken(String refreshToken);
    void deleteById(String email);
}
