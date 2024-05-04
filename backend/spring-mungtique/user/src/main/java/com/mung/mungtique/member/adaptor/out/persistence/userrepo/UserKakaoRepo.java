package com.mung.mungtique.member.adaptor.out.persistence.userrepo;

import com.mung.mungtique.member.domain.UserKakao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKakaoRepo extends JpaRepository<UserKakao, Long> {

    UserKakao findByUsername(String username);
}
