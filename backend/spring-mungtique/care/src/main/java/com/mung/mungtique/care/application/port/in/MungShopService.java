package com.mung.mungtique.care.application.port.in;

import com.mung.mungtique.care.adaptor.in.web.dto.MungShopLikeReq;
import com.mung.mungtique.care.adaptor.in.web.dto.MungShopRes;
import com.mung.mungtique.care.domain.MungShopLike;

import java.util.List;

public interface MungShopService {
    List<MungShopRes> getAllMungShops();

    MungShopLike likeMungShop(MungShopLikeReq mungShopLikeReq);

    boolean unlikeMungShop(MungShopLikeReq mungShopLikeReq);

    Boolean likeMungShopStatus(Long mungShopId, Long userId);
}
