package com.mung.mungtique.member.adaptor.out.persistence.userrepo;

import com.mung.mungtique.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);
}
