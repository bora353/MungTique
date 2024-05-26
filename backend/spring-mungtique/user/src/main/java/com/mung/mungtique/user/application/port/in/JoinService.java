package com.mung.mungtique.user.application.port.in;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.user.domain.User;

public interface JoinService {
    User joinProcess(JoinDTO joinDTO);
}
