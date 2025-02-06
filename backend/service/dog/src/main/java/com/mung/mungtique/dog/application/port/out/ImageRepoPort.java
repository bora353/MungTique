package com.mung.mungtique.dog.application.port.out;


import com.mung.mungtique.dog.domain.Image;
import com.mung.mungtique.dog.domain.Dog;

public interface ImageRepoPort {
    Image findByDog(Dog myMung);

    Image save(Image image);
}
