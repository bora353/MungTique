package com.mung.mungtique.care.application.service;

import com.mung.mungtique.care.adaptor.in.web.dto.MungJoinReq;
import com.mung.mungtique.care.application.port.in.MungService;
import com.mung.mungtique.care.application.port.out.MungPort;
import com.mung.mungtique.care.application.service.mapper.MungMapper;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MungServiceImpl implements MungService {

    private final MungPort mungPort;
    private final MungMapper mapper;


    @Override
    public MyMung join(MungJoinReq mungJoinReq) {
        MyMung myMung = mapper.dtoToDomain(mungJoinReq);

        return mungPort.join(myMung);
    }
}
