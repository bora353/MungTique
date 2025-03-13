package com.mung.mungtique.payment.adaptor.out.message.dto;

import lombok.Builder;

@Builder
public record PaymentSuccessMessage(
        Long paymentId,
        Long reservationId
) {
}
