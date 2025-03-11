package com.mung.mungtique.mungshop.application.port.in;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.ServiceType;

import java.util.List;

public interface MungShopService {
    List<MungShopRes> getAllMungShops();
    List<MungShopRes> findMungShopsByQuery(String searchQuery);
    Integer getPrice(Long mungShopId, BreedType breedType, ServiceType serviceType);
}
