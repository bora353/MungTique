package com.mung.mungtique.user.application.port.out;

import com.mung.mungtique.user.domain.User;

public interface UserRepoPort {

    Boolean existsByEmail(String email);

    User save(User user);

    User findByEmail(String email);
}
