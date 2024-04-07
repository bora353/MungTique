package com.mung.mungtique.member.adaptor.out.persistence;

import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmail(String email);
}
