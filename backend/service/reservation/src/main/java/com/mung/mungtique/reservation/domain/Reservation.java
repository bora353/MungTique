package com.mung.mungtique.reservation.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(nullable = false)
    private Long mungShopId;

    @Column(length = 20, nullable = false)
    private String storeName;

    @Column(length = 50, nullable = false)
    private String dogName;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(length = 100)
    private LocalDate reservationDate;

    @Column(length = 100)
    private String reservationTime;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String phone;

    @Column(length = 100)
    private String requestMessage;
}
