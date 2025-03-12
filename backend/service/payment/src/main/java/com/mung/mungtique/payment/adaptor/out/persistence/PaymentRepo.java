package com.mung.mungtique.payment.adaptor.out.persistence;

import com.mung.mungtique.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
}
