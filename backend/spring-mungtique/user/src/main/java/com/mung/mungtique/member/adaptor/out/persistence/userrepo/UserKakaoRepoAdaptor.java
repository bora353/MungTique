package com.mung.mungtique.member.adaptor.out.persistence.userrepo;

import com.mung.mungtique.member.application.port.out.UserKakaoRepoPort;
import com.mung.mungtique.member.domain.UserKakao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserKakaoRepoAdaptor implements UserKakaoRepoPort {

    private final UserKakaoRepo userKakaoRepo;

    @Override
    public UserKakao findByUsername(String username) {
        return userKakaoRepo.findByUsername(username);
    }

    @Override
    public UserKakao save(UserKakao userKakao) {
        return userKakaoRepo.save(userKakao);
    }
}
