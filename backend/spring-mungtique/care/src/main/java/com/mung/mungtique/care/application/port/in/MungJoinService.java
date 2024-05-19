package com.mung.mungtique.care.application.port.in;

import com.mung.mungtique.care.adaptor.in.web.dto.MungJoinReq;
import com.mung.mungtique.care.domain.MyMung;

public interface MungJoinService {
    MyMung join(MungJoinReq mungJoinReq);
}
