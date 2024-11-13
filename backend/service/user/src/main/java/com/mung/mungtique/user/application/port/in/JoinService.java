package com.mung.mungtique.user.application.port.in;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JoinService extends UserDetailsService {
    JoinRes createUser(JoinReq joinReq);
}
