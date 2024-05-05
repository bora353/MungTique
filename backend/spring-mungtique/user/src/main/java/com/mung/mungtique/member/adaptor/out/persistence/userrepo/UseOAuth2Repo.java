package com.mung.mungtique.member.adaptor.out.persistence.userrepo;

import com.mung.mungtique.member.domain.UserOAuth2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UseOAuth2Repo extends JpaRepository<UserOAuth2, Long> {

    UserOAuth2 findByUsername(String username);
}
