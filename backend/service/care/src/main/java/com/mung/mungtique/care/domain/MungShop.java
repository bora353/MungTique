package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(length = 100)
    private String storeAddress;

    @Column(length = 100)
    private String breeds;

    @Column(length = 50)
    private String businessHours;

    @Column(length = 50)
    private String closingDays;

    @Column(length = 30)
    private String latitude ;

    @Column(length = 30)
    private String longitude;

    @Column(length = 200)
    private String filePath;

    @OneToMany(mappedBy = "mungShop", cascade = CascadeType.ALL)
    private List<MungShopPrice> prices;

    @OneToMany(mappedBy = "mungShop", cascade = CascadeType.ALL)
    private List<MungShopLike> likes;
}
