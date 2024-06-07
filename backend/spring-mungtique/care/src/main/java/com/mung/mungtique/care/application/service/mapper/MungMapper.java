package com.mung.mungtique.care.application.service.mapper;

import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungJoinReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungRes;
import com.mung.mungtique.care.domain.MyMung;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface MungMapper {

    MyMung dtoToDomain(MungJoinReq mungJoinReq);

    List<MungRes> domainListToDtoList(List<MyMung> byUserId);

    MungRes domainToDto(MyMung myMung);
}
