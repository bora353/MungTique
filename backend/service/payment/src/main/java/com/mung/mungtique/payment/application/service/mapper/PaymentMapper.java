package com.mung.mungtique.payment.application.service.mapper;

import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentReq;
import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentRes;
import com.mung.mungtique.payment.domain.Payment;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface PaymentMapper {

    PaymentRes toPaymentRes(Payment payment);

    Payment toPayment(PaymentReq paymentRequest);
}
