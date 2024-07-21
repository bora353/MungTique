package com.mung.mungtique.user.adaptor.out.persistence.userrepo;

import com.mung.mungtique.user.domain.UserOAuth2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UseOAuth2Repo extends JpaRepository<UserOAuth2, Long> {

    UserOAuth2 findByUsername(String username);
}
