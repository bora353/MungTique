package com.mung.mungtique.payment.application.port.in;

import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentReq;
import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentRes;

public interface PaymentService {
    long processPayment(PaymentReq paymentRequest);
    PaymentRes getPayment(Long paymentId);
}
