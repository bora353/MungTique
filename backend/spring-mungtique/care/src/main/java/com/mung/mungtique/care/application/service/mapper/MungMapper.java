package com.mung.mungtique.care.application.service.mapper;

import com.mung.mungtique.care.adaptor.in.web.dto.MungJoinReq;
import com.mung.mungtique.care.adaptor.in.web.dto.MungShopRes;
import com.mung.mungtique.care.domain.MungShop;
import com.mung.mungtique.care.domain.MyMung;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface MungMapper {

    MyMung dtoToDomain(MungJoinReq mungJoinReq);
}
