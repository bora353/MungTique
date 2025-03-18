package com.mung.mungtique.payment.adaptor.out.persistence;

import com.mung.mungtique.payment.application.port.out.persistence.PaymentRepoPort;
import com.mung.mungtique.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepoAdaptor implements PaymentRepoPort {

    private final PaymentRepo paymentRepo;

    @Override
    public Payment save(Payment reservation) {
        return paymentRepo.save(reservation);
    }

    @Override
    public List<Payment> findByUserId(Long userId) {
        return paymentRepo.findByUserId(userId);
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        return paymentRepo.findById(paymentId);
    }
}
