package com.mung.mungtique.care.application.port.out;

import com.mung.mungtique.care.domain.Image;
import com.mung.mungtique.care.domain.MyMung;

public interface ImagePort {
    Image findByMyMung(MyMung myMung);

    Image save(Image image);
}
