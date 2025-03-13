package com.mung.mungtique.payment.application.port.out.message;

import com.mung.mungtique.payment.adaptor.out.message.dto.PaymentSuccessMessage;

public interface ReservationEventPort {

    void sendPaymentSuccessEvent(PaymentSuccessMessage message);
}
