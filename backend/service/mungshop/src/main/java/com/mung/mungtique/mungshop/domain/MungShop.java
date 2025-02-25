package com.mung.mungtique.mungshop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "mung_shop")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MungShop extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopId;

    @Column(length = 20, nullable = false)
    private String storeName;

    @Column(length = 100, nullable = false)
    private String storeAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private BreedType breedType;

    @Column(length = 50, nullable = false)
    private String businessHours;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ClosingDayType closingDays;

    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal longitude;

    @Column(length = 200)
    private String storeImageUrl;

    @OneToMany(mappedBy = "mungShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MungShopPrice> prices;

//    @OneToMany(mappedBy = "mungShop", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MungShopLike> likes;
}
