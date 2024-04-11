package com.mung.mungtique.member.application.port.out;

import com.mung.mungtique.member.domain.User;

public interface UserRepoPort {

    Boolean existsByEmail(String email);

    User save(User user);

    User findByEmail(String email);
}
