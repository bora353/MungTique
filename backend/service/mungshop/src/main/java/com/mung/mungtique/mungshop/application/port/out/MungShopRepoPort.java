package com.mung.mungtique.mungshop.application.port.out;

import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopLike;

import java.util.List;
import java.util.Optional;

public interface MungShopRepoPort {
    List<MungShop> findAll();

    MungShopLike save(MungShopLike mungShopLike);

    Optional<MungShopLike> findByMungShopMungShopIdAndUserId(Long mungShopId, Long userId);

    void delete(MungShopLike mungShopLike);

    Optional<MungShop> findById(Long mungShopId);
}
