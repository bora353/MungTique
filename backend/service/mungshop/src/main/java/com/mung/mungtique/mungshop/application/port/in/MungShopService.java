package com.mung.mungtique.mungshop.application.port.in;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;

import java.util.List;

public interface MungShopService {
    List<MungShopRes> getAllMungShops();
    Boolean likeMungShopStatus(Long mungShopId, Long userId);
    MungShopLikeRes likeMungShop(Long mungShopId, Long userId);
    boolean unlikeMungShop(Long mungShopId, Long userId);

    List<MungShopRes> findMungShopsByQuery(String searchQuery);
}
