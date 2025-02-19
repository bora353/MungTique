package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
