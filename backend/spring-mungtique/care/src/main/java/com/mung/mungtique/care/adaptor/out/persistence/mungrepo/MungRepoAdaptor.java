package com.mung.mungtique.care.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.care.application.port.out.MungPort;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MungRepoAdaptor implements MungPort {

    private final MungJoinRepo mungJoinRepo;

    @Override
    public MyMung join(MyMung myMung) {
        return mungJoinRepo.save(myMung);
    }

    @Override
    public Optional<MyMung> findByid(Long mungId) {
        return mungJoinRepo.findById(mungId);
    }
}
