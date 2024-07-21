package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mung_shop_like")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MungShopLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mung_shop_id")
    private MungShop mungShop;

    @Column(name = "user_id")
    private Long userId;

}
