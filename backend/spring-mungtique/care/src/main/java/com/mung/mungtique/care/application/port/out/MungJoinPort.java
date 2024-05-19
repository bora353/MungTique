package com.mung.mungtique.care.application.port.out;

import com.mung.mungtique.care.domain.MyMung;

public interface MungJoinPort {
    MyMung join(MyMung myMung);
}
