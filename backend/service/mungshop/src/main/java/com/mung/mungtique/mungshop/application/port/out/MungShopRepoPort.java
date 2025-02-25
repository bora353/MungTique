package com.mung.mungtique.mungshop.application.port.out;

import com.mung.mungtique.mungshop.domain.MungShop;

import java.util.List;
import java.util.Optional;

public interface MungShopRepoPort {
    List<MungShop> findAll();

    Optional<MungShop> findById(Long mungShopId);

    List<MungShop> findByStoreName(String searchQuery);
}
