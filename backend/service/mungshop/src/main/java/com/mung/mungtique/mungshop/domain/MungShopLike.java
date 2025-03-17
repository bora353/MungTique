package com.mung.mungtique.mungshop.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mung_shop_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MungShopLike extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopLikeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mung_shop_id", nullable = false)
    private MungShop mungShop;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    private MungShopLike(Long mungShopLikeId, MungShop mungShop, Long userId) {
        this.mungShopLikeId = mungShopLikeId;
        this.mungShop = mungShop;
        this.userId = userId;
    }
}
