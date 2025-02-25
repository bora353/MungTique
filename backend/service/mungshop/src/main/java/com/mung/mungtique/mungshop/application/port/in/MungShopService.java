package com.mung.mungtique.mungshop.application.port.in;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;

import java.util.List;

public interface MungShopService {
    List<MungShopRes> getAllMungShops();
    List<MungShopRes> findMungShopsByQuery(String searchQuery);
}
