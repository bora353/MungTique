package com.mung.mungtique.payment.adaptor.in.web.dto;

import com.mung.mungtique.payment.domain.PaymentMethod;
import lombok.Builder;

@Builder
public record PaymentRes(
        Integer amount,
        PaymentMethod paymentMethod,
        String cardNumber,
        String bankName,
        String accountNumber
) {}