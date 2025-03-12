package com.mung.mungtique.payment.adaptor.in.web.dto;


import com.mung.mungtique.payment.domain.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record PaymentReq(
        @NotNull(message = "예약 ID는 필수입니다.")
        Long reservationId,

        @NotNull(message = "유저 ID는 필수입니다.")
        Long userId,

        @NotNull(message = "결제 금액은 필수입니다.")
        @Positive(message = "결제 금액은 0보다 커야 합니다.")
        Integer amount,

        @NotBlank(message = "결제 방법은 필수입니다.")
        @Pattern(regexp = "CARD|BANK_TRANSFER", message = "유효한 결제 방법이 아닙니다. (CARD 또는 BANK_TRANSFER)")
        PaymentMethod paymentMethod,

        // 카드 결제 정보
        @Pattern(regexp = "\\d{16}", message = "카드 번호는 16자리 숫자여야 합니다.")
        String cardNumber,

        @Pattern(regexp = "\\d{2}/\\d{2}", message = "카드 유효기간은 MM/YY 형식이어야 합니다.")
        String cardExpiry,

        @Pattern(regexp = "\\d{3,4}", message = "CVC는 3~4자리 숫자여야 합니다.")
        String cardCVC,

        // 계좌이체 정보
        String accountHolder,

        String bankName,

        @Pattern(regexp = "\\d{10,14}", message = "계좌번호는 10~14자리 숫자여야 합니다.")
        String accountNumber
) {}