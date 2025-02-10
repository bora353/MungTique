package com.mung.mungtique.dog.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.dog.application.port.out.ImageRepoPort;
import com.mung.mungtique.dog.domain.Dog;
import com.mung.mungtique.dog.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImageRepoAdaptor implements ImageRepoPort {

    private final ImageRepo imageRepo;

    @Override
    public Optional<Image> findByDog(Dog myMung) {
        return Optional.ofNullable(imageRepo.findByDog(myMung));
    }

    @Override
    public Image save(Image image) {
        return imageRepo.save(image);
    }
}
