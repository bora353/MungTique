package com.mung.mungtique.care.application.service.mapper;

import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.care.domain.MungShop;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface MungShopMapper {

    List<MungShopRes> domainToDto(List<MungShop> mungShop);
}
