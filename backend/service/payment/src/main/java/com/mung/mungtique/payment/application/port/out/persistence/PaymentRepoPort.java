package com.mung.mungtique.payment.application.port.out.persistence;



import com.mung.mungtique.payment.domain.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepoPort {
    Payment save(Payment reservation);
    List<Payment> findByUserId(Long userId);
    Optional<Payment> findById(Long paymentId);
}
