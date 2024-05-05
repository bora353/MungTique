package com.mung.mungtique.member.adaptor.out.persistence.userrepo;

import com.mung.mungtique.member.application.port.out.UserOAuth2RepoPort;
import com.mung.mungtique.member.domain.UserOAuth2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserOAuth2RepoAdaptor implements UserOAuth2RepoPort {

    private final UseOAuth2Repo useOAuth2Repo;

    @Override
    public UserOAuth2 findByUsername(String username) {
        return useOAuth2Repo.findByUsername(username);
    }

    @Override
    public UserOAuth2 save(UserOAuth2 userOAuth2) {
        return useOAuth2Repo.save(userOAuth2);
    }
}
