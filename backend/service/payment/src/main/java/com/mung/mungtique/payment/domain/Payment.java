package com.mung.mungtique.payment.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "payment")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // 카드 결제 정보
    @Column(length = 16)
    private String cardNumber;

    @Column(length = 5)
    private String cardExpiry;

    @Column(length = 4)
    private String cardCVC;

    // 계좌이체 정보
    private String accountHolder;

    private String bankName;

    @Column(length = 14)
    private String accountNumber;

    private Boolean isDeleted = false;
}
