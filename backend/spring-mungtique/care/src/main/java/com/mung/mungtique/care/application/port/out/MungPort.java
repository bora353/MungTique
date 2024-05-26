package com.mung.mungtique.care.application.port.out;

import com.mung.mungtique.care.domain.MyMung;

import java.util.Optional;

public interface MungPort {
    MyMung join(MyMung myMung);

    Optional<MyMung> findByid(Long mungId);
}
