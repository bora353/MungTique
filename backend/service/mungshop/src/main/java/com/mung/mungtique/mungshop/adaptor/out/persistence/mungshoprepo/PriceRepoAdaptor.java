package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.application.port.out.PriceRepoPort;
import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.MungShopPrice;
import com.mung.mungtique.mungshop.domain.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PriceRepoAdaptor implements PriceRepoPort {

    private final PriceRepo priceRepo;

    @Override
    public Optional<MungShopPrice> findPriceByShopAndType(Long mungShopId, BreedType breedType, ServiceType serviceType) {
        return priceRepo.findByMungShop_MungShopIdAndBreedTypeAndServiceType(mungShopId, breedType, serviceType);
    }
}
