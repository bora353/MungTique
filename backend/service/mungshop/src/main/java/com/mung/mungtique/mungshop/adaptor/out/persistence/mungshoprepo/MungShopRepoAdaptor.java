package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.application.port.out.MungShopRepoPort;
import com.mung.mungtique.mungshop.domain.MungShop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MungShopRepoAdaptor implements MungShopRepoPort {

    private final MungShopRepo mungShopRepo;

    @Override
    public List<MungShop> findAll() {
        return mungShopRepo.findAll();
    }

    @Override
    public Optional<MungShop> findById(Long mungShopId) {
        return mungShopRepo.findById(mungShopId);
    }

    @Override
    public List<MungShop> findByStoreName(String searchQuery) {
        return mungShopRepo.findByStoreNameContaining(searchQuery);
    }
}
