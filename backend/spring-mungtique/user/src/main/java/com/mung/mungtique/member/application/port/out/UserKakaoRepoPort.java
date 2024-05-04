package com.mung.mungtique.member.application.port.out;

import com.mung.mungtique.member.domain.UserKakao;

public interface UserKakaoRepoPort {
    UserKakao findByUsername(String username);

    UserKakao save(UserKakao userKakao);
}
