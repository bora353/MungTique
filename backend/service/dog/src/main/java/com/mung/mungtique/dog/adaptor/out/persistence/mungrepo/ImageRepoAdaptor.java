package com.mung.mungtique.dog.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.dog.application.port.out.ImageRepoPort;
import com.mung.mungtique.dog.domain.Dog;
import com.mung.mungtique.dog.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepoAdaptor implements ImageRepoPort {

    private final ImageRepo imageRepo;

    @Override
    public Image findByDog(Dog myMung) {
        return imageRepo.findByDog(myMung);
    }

    @Override
    public Image save(Image image) {
        return imageRepo.save(image);
    }
}
