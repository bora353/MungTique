package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepoPort {

    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findById(String email);
    void deleteById(String email);
}
