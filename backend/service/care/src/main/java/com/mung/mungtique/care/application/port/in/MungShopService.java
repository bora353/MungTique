package com.mung.mungtique.care.application.port.in;

import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopRes;

import java.util.List;

public interface MungShopService {
    List<MungShopRes> getAllMungShops();

    MungShopLikeRes likeMungShop(MungShopLikeReq mungShopLikeReq);

    boolean unlikeMungShop(MungShopLikeReq mungShopLikeReq);

    Boolean likeMungShopStatus(Long mungShopId, Long userId);
}
