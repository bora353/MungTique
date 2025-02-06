package com.mung.mungtique.dog.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.dog.domain.Image;
import com.mung.mungtique.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepo extends JpaRepository<Image, Long> {
    Image findByDog(Dog myMung);
}
