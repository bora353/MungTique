package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.UserEntity;

public interface UserRepoPort {

    Boolean existsByEmail(String email);
    UserEntity save(UserEntity userEntity);
    UserEntity findByEmail(String email);
}
