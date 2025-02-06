package com.mung.mungtique.dog.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.dog.application.port.out.DogRepoPort;
import com.mung.mungtique.dog.domain.Dog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DogRepoAdaptor implements DogRepoPort {

    private final DogRepo dogRepo;

    @Override
    public Dog save(Dog dog) {
        return dogRepo.save(dog);
    }

    @Override
    public Optional<Dog> findById(Long DogId) {
        return dogRepo.findById(DogId);
    }

    @Override
    public List<Dog> findByUserId(Long userId) {
        return dogRepo.findByUserId(userId);
    }
}
