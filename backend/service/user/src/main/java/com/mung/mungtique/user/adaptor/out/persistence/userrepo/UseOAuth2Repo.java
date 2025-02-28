package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UseOAuth2Repo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId);
}
