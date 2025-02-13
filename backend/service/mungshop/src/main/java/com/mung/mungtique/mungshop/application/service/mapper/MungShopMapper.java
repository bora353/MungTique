package com.mung.mungtique.mungshop.application.service.mapper;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface MungShopMapper {

    @Mapping(source="mungShop.prices", target="mungShopPrices")
    MungShopRes toMungShopDTO(MungShop mungShop);

    //List<MungShopRes> mungShopListToMungshopResList(List<MungShop> mungShop);
    MungShopLikeRes toLikeDTO(MungShopLike mungShop);
}
