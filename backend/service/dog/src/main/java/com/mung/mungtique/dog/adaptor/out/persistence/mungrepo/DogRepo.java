package com.mung.mungtique.dog.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepo extends JpaRepository<Dog, Long> {
    List<Dog> findByUserId(Long userId);
}
