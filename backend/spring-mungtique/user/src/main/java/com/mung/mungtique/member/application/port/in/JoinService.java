package com.mung.mungtique.member.application.port.in;

import com.mung.mungtique.member.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.member.domain.User;

public interface JoinService {
    User joinProcess(JoinDTO joinDTO);
}
