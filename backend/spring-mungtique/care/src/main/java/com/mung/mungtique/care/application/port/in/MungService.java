package com.mung.mungtique.care.application.port.in;

import com.mung.mungtique.care.adaptor.in.web.dto.MungJoinReq;
import com.mung.mungtique.care.domain.MyMung;

public interface MungService {
    MyMung join(MungJoinReq mungJoinReq);
}
