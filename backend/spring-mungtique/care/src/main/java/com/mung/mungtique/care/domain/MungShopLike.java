package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shop_like")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MungShopLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopLikeId;

    @Column(length = 20)
    private Long mungShopId;

    @Column(length = 20)
    private Long userId;

}
