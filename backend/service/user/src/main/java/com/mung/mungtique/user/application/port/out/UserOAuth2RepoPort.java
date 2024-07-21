package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.UserOAuth2;

public interface UserOAuth2RepoPort {
    UserOAuth2 findByUsername(String username);

    UserOAuth2 save(UserOAuth2 userOAuth2);
}
