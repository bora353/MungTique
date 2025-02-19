package com.mung.mungtique.user.application.port.in;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import com.mung.mungtique.user.adaptor.in.web.dto.UserRes;
import com.mung.mungtique.user.domain.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    JoinRes createUser(JoinReq joinReq);
    UserEntity getUserDetailsByEmail(String email);

    UserRes getUserInfo(@NotEmpty String userId);
}
