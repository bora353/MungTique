package com.mung.mungtique.payment.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Builder
    private Payment(Long paymentId, Long reservationId, Long userId, Integer amount, PaymentMethod paymentMethod, String cardNumber, String cardExpiry, String cardCVC, String accountHolder, String bankName, String accountNumber, Boolean isDeleted, PaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.cardCVC = cardCVC;
        this.accountHolder = accountHolder;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.isDeleted = isDeleted;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public void completePayment() {
        this.paymentStatus = PaymentStatus.SUCCESS;
    }

    public void processRefund() {
        this.paymentStatus = PaymentStatus.REFUNDED;
        this.isDeleted = true;
    }
}
