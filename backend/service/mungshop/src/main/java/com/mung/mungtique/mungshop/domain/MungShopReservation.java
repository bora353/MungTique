package com.mung.mungtique.mungshop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mung_shop_reservation")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MungShopReservation extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mungShopReservationId;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private String reservationTime;

    @Enumerated(EnumType.STRING)
    private MungShopReservationStatus status;

    private Long mungShopId;
}
