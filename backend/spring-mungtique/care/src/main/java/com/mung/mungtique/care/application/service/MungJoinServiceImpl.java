package com.mung.mungtique.care.application.service;

import com.mung.mungtique.care.adaptor.in.web.dto.MungJoinReq;
import com.mung.mungtique.care.application.port.in.MungJoinService;
import com.mung.mungtique.care.application.port.out.MungJoinPort;
import com.mung.mungtique.care.application.service.mapper.MungMapper;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MungJoinServiceImpl implements MungJoinService {

    private final MungJoinPort mungJoinPort;
    private final MungMapper mapper;


    @Override
    public MyMung join(MungJoinReq mungJoinReq) {
        MyMung myMung = mapper.dtoToDomain(mungJoinReq);

        return mungJoinPort.join(myMung);
    }
}
