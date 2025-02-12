package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.domain.MungShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MungShopRepo extends JpaRepository<MungShop, Long> {

    List<MungShop> findByStoreNameContaining(String searchQuery);
}
