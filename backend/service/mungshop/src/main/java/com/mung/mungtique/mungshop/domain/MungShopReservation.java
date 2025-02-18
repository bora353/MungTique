package com.mung.mungtique.mungshop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime reservationDateTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Long mungShopId;
}
