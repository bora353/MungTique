package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.application.port.out.MungShopRepoPort;
import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MungShopRepoAdaptor implements MungShopRepoPort {

    private final MungShopRepo mungShopRepo;
    private final MungShopLikeRepo mungShopLikeRepo;

    @Override
    public List<MungShop> findAll() {
        return mungShopRepo.findAll();
    }

    @Override
    public MungShopLike save(MungShopLike mungShopLike) {
        return mungShopLikeRepo.save(mungShopLike);
    }

    @Override
    public Optional<MungShopLike> findByMungShopMungShopIdAndUserId(Long mungShopId, Long userId) {
        return mungShopLikeRepo.findByMungShopMungShopIdAndUserId(mungShopId, userId);
    }

    @Override
    public void delete(MungShopLike mungShopLike) {
        mungShopLikeRepo.delete(mungShopLike);
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
