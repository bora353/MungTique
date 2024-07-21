package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mung_shop_price")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MungShopPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopPriceId;

    @Column(length = 100)
    private String breeds;

    @Column(length = 50)
    private String serviceType;

    @Column(length = 20)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mung_shop_id")
    private MungShop mungShop;
}
