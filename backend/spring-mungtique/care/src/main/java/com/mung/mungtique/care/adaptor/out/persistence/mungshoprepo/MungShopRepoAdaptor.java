package com.mung.mungtique.care.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.care.application.port.out.MungShopRepoPort;
import com.mung.mungtique.care.domain.MungShop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MungShopRepoAdaptor implements MungShopRepoPort {

    private final MungShopRepo mungShopRepo;


    @Override
    public List<MungShop> findAll() {
        return mungShopRepo.findAll();
    }
}
