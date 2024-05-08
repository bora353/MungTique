package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mung_shop")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MungShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
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


}
