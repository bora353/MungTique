package com.mung.mungtique.reservation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(nullable = false)
    private Long mungShopId;

    @Column(length = 20, nullable = false)
    private String storeName;

    @Column(nullable = false)
    private Long dogId;

    @Column(length = 50, nullable = false)
    private String dogName;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private BreedType breedType;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(length = 100)
    private LocalDate reservationDate;

    @Column(length = 100)
    private String reservationTime;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String phone;

    @Column(length = 100)
    private String requestMessage;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Boolean isDeleted = false;

    private Long paymentId;

    @Builder
    private Reservation(Long reservationId, Long mungShopId, String storeName, Long dogId, String dogName, BreedType breedType, ServiceType serviceType, LocalDate reservationDate, String reservationTime, Long userId, String username, String phone, String requestMessage, ReservationStatus reservationStatus, Boolean isDeleted, Long paymentId) {
        this.reservationId = reservationId;
        this.mungShopId = mungShopId;
        this.storeName = storeName;
        this.dogId = dogId;
        this.dogName = dogName;
        this.breedType = breedType;
        this.serviceType = serviceType;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.requestMessage = requestMessage;
        this.reservationStatus = ReservationStatus.WAITING_FOR_PAYMENT;
        this.isDeleted = isDeleted;
        this.paymentId = paymentId;
    }

    public void confirmPayment(Long paymentId) {
        this.paymentId = paymentId;
        this.reservationStatus = ReservationStatus.PAID;
    }

    public void cancel() {
        this.reservationStatus = ReservationStatus.CANCELED;
        this.isDeleted = true;
    }
}
