package com.mung.mungtique.user.application.port.in;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;

public interface JoinService {
    JoinRes registerUser(JoinReq joinReq);
}
