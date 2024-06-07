package com.mung.mungtique.care.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.care.domain.MyMung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MungRepo extends JpaRepository<MyMung, Long> {
    List<MyMung> findByUserId(Long userId);
}
