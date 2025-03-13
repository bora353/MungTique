package com.mung.mungtique.reservation.adaptor.in.message.dto;

import lombok.Builder;

@Builder
public record PaymentSuccessMessage(
        Long paymentId,
        Long reservationId
) {
}
