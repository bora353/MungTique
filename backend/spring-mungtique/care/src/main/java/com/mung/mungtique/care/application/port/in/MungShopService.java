package com.mung.mungtique.care.application.port.in;

import com.mung.mungtique.care.adaptor.in.web.dto.MungShopRes;

import java.util.List;

public interface MungShopService {
    List<MungShopRes> getAllMungShops();
}
