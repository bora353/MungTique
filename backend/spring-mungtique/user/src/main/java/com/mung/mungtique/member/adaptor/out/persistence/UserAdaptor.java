package com.mung.mungtique.member.adaptor.out.persistence;

import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;
import com.mung.mungtique.member.application.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAdaptor implements UserPort {

    private final UserJpaRepo userJpaRepo;

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepo.existsByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userJpaRepo.save(userEntity);
    }
}
