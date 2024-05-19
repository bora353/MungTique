package com.mung.mungtique.care.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.care.application.port.out.MungJoinPort;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MungJoinRepoAdaptor implements MungJoinPort {

    private final MungJoinRepo mungJoinRepo;

    @Override
    public MyMung join(MyMung myMung) {
        return mungJoinRepo.save(myMung);
    }
}
