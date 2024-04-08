package com.mung.mungtique.member.application.port.out;

import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;

public interface UserPort {

    Boolean existsByEmail(String email);

    UserEntity save(UserEntity userEntity);

    UserEntity findByEmail(String email);
}
