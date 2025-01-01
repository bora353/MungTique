package com.mung.mungtique.care.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.care.application.port.out.ImageRepoPort;
import com.mung.mungtique.care.domain.Image;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepoAdaptor implements ImageRepoPort {

    private final ImageRepo imageRepo;

    @Override
    public Image findByMyMung(MyMung myMung) {
        return imageRepo.findByMyMung(myMung);
    }

    @Override
    public Image save(Image image) {
        return imageRepo.save(image);
    }
}
