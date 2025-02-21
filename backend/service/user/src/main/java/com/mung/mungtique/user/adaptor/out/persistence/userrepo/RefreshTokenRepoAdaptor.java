package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.application.port.out.RefreshTokenRepoPort;
import com.mung.mungtique.user.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepoAdaptor implements RefreshTokenRepoPort {

    private final RefreshTokenRepo tokenRepo;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return tokenRepo.save(refreshToken);
    }

    @Override
    public Boolean existByRefreshToken(String refreshToken) {
        return tokenRepo.existsByRefreshToken(refreshToken);
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        tokenRepo.deleteByRefreshToken(refreshToken);
    }

    @Override
    public void deleteById(String email) {
        tokenRepo.deleteById(email);
    }
}
