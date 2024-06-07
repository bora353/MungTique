package com.mung.mungtique.care.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.care.domain.MungShopLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MungShopLikeRepo extends JpaRepository<MungShopLike, Long> {

    Optional<MungShopLike> findByMungShopMungShopIdAndUserId(Long mungShopId, Long userId);

}
