package com.mung.mungtique.mungshop.application.service.mapper;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopLike;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface MungShopMapper {

    List<MungShopRes> domainListToDtoList(List<MungShop> mungShop);
    MungShopLikeRes domainToDto(MungShopLike mungShop);
}
