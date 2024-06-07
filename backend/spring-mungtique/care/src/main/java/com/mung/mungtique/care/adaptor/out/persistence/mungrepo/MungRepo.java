package com.mung.mungtique.care.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.care.domain.MyMung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MungJoinRepo extends JpaRepository<MyMung, Long> {
}
