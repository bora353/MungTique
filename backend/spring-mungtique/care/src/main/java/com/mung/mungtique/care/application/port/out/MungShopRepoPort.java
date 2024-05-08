package com.mung.mungtique.care.application.port.out;

import com.mung.mungtique.care.domain.MungShop;

import java.util.List;

public interface MungShopRepoPort {
    List<MungShop> findAll();
}
