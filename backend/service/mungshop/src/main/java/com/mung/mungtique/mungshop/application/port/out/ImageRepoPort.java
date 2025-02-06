package com.mung.mungtique.mungshop.application.port.out;

import com.mung.mungtique.mungshop.domain.Image;

public interface ImageRepoPort {
    Image save(Image image);
}
