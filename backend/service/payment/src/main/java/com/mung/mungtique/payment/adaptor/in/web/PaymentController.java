package com.mung.mungtique.payment.adaptor.in.web;

import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentReq;
import com.mung.mungtique.payment.adaptor.in.web.dto.PaymentRes;
import com.mung.mungtique.payment.application.port.in.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 요청 처리")
    @PostMapping("/payments")
    public ResponseEntity<Long> processPayment(@Valid @RequestBody PaymentReq paymentRequest) {
        return ResponseEntity.ok(paymentService.processPayment(paymentRequest));
    }

    @Operation(summary = "결제 번호로 결제 목록 조회")
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentRes> getPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }
}
