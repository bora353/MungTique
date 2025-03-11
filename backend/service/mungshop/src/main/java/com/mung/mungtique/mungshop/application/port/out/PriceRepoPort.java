package com.mung.mungtique.mungshop.application.port.out;

import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.MungShopPrice;
import com.mung.mungtique.mungshop.domain.ServiceType;

import java.util.Optional;

public interface PriceRepoPort {

    Optional<MungShopPrice> findPriceByShopAndType(Long mungShopId, BreedType breedType, ServiceType serviceType);
}
