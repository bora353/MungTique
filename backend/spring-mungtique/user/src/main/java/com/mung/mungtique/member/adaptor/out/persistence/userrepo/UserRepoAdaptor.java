package com.mung.mungtique.member.adaptor.out.persistence.userrepo;

import com.mung.mungtique.member.domain.User;
import com.mung.mungtique.member.application.port.out.UserRepoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepoAdaptor implements UserRepoPort {

    private final UserRepo userRepo;

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}