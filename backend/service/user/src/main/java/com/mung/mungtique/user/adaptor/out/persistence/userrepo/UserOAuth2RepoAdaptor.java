package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.application.port.out.UserOAuth2RepoPort;
import com.mung.mungtique.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserOAuth2RepoAdaptor implements UserOAuth2RepoPort {

    private final UseOAuth2Repo useOAuth2Repo;

    @Override
    public UserEntity save(UserEntity user) {
        return useOAuth2Repo.save(user);
    }

    @Override
    public Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId) {
        return useOAuth2Repo.findByProviderAndProviderId(provider, providerId);
    }
}
