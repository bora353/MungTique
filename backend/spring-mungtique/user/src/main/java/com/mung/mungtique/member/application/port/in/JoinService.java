package com.mung.mungtique.member.application.port.in;

import com.mung.mungtique.member.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;

public interface JoinService {
    UserEntity joinProcess(JoinDTO joinDTO);
}
