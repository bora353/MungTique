package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.UserEntity;

import java.util.Optional;

public interface UserOAuth2RepoPort {
    UserEntity save(UserEntity userOAuth2);
    Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId);
}
