package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.domain.MungShopLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MungShopLikeRepo extends JpaRepository<MungShopLike, Long> {

    Optional<MungShopLike> findByMungShopMungShopIdAndUserId(Long mungShopId, Long userId);

}
