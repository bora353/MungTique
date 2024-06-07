package com.mung.mungtique.care.application.port.out;

import com.mung.mungtique.care.domain.MungShop;
import com.mung.mungtique.care.domain.MungShopLike;

import java.util.List;
import java.util.Optional;

public interface MungShopRepoPort {
    List<MungShop> findAll();

    MungShopLike save(MungShopLike mungShopLike);

    Optional<MungShopLike> findByMungShopMungShopIdAndUserId(Long mungShopId, Long userId);

    void delete(MungShopLike mungShopLike);

    Optional<MungShop> findById(Long mungShopId);
}
