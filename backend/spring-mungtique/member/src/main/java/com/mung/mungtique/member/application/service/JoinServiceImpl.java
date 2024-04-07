package com.mung.mungtique.member.application.service;

import com.mung.mungtique.member.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;
import com.mung.mungtique.member.application.port.in.JoinService;
import org.springframework.stereotype.Service;

@Service
public class JoinServiceImpl implements JoinService {
    @Override
    public UserEntity joinProcess(JoinDTO joinDTO) {
        return null;
    }
}
