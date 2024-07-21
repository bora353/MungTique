package com.mung.mungtique.care.application.port.in;

import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungJoinReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungRes;
import com.mung.mungtique.care.domain.MyMung;

import java.util.List;

public interface MungService {
    MyMung join(MungJoinReq mungJoinReq);

    List<MungRes> getMyMungs(Long userId);
}
