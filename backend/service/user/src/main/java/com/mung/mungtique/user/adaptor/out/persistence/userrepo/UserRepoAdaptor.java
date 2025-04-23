package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.application.port.out.UserRepoPort;
import com.mung.mungtique.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepoAdaptor implements UserRepoPort {

    private final UserRepo userRepo;

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId) {
        return userRepo.findByProviderAndProviderId(provider, providerId);
    }
}
