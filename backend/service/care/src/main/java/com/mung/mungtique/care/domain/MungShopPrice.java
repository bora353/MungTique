package com.mung.mungtique.care.domain;

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

    @Column(length = 50)
    private String serviceType;

    @Column(length = 20)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mung_shop_id")
    private MungShop mungShop;
}
