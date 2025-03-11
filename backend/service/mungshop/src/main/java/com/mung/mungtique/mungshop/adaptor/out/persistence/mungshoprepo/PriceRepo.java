package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.MungShopPrice;
import com.mung.mungtique.mungshop.domain.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepo extends JpaRepository<MungShopPrice, Long> {

    Optional<MungShopPrice> findByMungShop_MungShopIdAndBreedTypeAndServiceType(Long mungShopId, BreedType breedType, ServiceType serviceType);
}
