package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.UserEntity;

import java.util.Optional;

public interface UserRepoPort {

    Boolean existsByEmail(String email);
    UserEntity save(UserEntity userEntity);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long userId);
    Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId);
}
