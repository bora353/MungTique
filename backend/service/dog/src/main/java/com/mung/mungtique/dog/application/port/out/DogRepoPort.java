package com.mung.mungtique.dog.application.port.out;


import com.mung.mungtique.dog.domain.Dog;

import java.util.List;
import java.util.Optional;

public interface DogRepoPort {
    Dog save(Dog myMung);
    Optional<Dog> findById(Long mungId);
    List<Dog> findByUserId(Long userId);
}
