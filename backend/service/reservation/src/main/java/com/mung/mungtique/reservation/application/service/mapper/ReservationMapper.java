package com.mung.mungtique.mungshop.application.service.mapper;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation.MungShopReservationRes;
import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopReservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "spring")
public interface MungShopMapper {

    @Mapping(source="mungShop.prices", target="mungShopPrices")
    MungShopRes toMungShopDTO(MungShop mungShop);

    List<MungShopReservationRes> toMungShopReservationRes(List<MungShopReservation> mungShopReservations);
}
