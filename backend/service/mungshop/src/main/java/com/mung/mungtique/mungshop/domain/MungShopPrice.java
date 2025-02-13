package com.mung.mungtique.mungshop.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mung_shop_price")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MungShopPrice extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopPriceId;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private BreedType breedType;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(length = 20, nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mung_shop_id", nullable = false)
    private MungShop mungShop;
}
