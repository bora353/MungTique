package com.mung.mungtique.care.adaptor.out.persistence.mungrepo;

import com.mung.mungtique.care.application.port.out.MungRepoPort;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MungRepoAdaptor implements MungRepoPort {

    private final MungRepo mungRepo;

    @Override
    public MyMung save(MyMung myMung) {
        return mungRepo.save(myMung);
    }

    @Override
    public Optional<MyMung> findByid(Long mungId) {
        return mungRepo.findById(mungId);
    }

    @Override
    public List<MyMung> findByUserId(Long userId) {
        return mungRepo.findByUserId(userId);
    }
}
