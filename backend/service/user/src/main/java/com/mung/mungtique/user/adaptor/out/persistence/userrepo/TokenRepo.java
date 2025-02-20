package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Token, Long>{

    Boolean existsByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
}
