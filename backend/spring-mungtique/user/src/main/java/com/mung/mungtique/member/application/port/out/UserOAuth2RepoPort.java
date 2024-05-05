package com.mung.mungtique.member.application.port.out;

import com.mung.mungtique.member.domain.UserOAuth2;

public interface UserOAuth2RepoPort {
    UserOAuth2 findByUsername(String username);

    UserOAuth2 save(UserOAuth2 userOAuth2);
}
