package com.mung.mungtique.mungshop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "mung_shop_reservation")
@Getter
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

    @Builder
    private MungShopReservation(Long mungShopReservationId, LocalDate reservationDate, String reservationTime, MungShopReservationStatus status, Long mungShopId) {
        this.mungShopReservationId = mungShopReservationId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.status = status;
        this.mungShopId = mungShopId;
    }

    public MungShopReservation confirmReservation(MungShopReservation reservation) {
        this.reservationDate = reservation.getReservationDate();
        this.reservationTime = reservation.getReservationTime();
        this.status = MungShopReservationStatus.BOOKED;

        return this;
    }

    public void changeAvailableTime() {
        this.status = MungShopReservationStatus.AVAILABLE;
    }
}
