package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.application.port.out.TokenRepoPort;
import com.mung.mungtique.user.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepoAdaptor implements TokenRepoPort {

    private final TokenRepo tokenRepo;

    @Override
    public Token save(Token token) {
        return tokenRepo.save(token);
    }

    @Override
    public Boolean existByRefreshToken(String refreshToken) {
        return tokenRepo.existsByRefreshToken(refreshToken);
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        tokenRepo.deleteByRefreshToken(refreshToken);
    }
}
