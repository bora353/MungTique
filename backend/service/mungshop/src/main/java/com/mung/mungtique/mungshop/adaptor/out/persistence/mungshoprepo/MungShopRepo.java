package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.domain.MungShop;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MungShopRepo extends JpaRepository<MungShop, Long> {

    @EntityGraph(attributePaths = {"prices"})
    @NonNull
    List<MungShop> findAll();

    List<MungShop> findByStoreNameContaining(String searchQuery);
}
