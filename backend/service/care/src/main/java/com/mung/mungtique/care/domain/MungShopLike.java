package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mung_shop_like")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MungShopLike extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mung_shop_id")
    private MungShop mungShop;

    @Column(name = "user_id")
    private Long userId;

}
