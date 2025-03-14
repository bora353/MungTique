package com.mung.mungtique.reservation.adaptor.in.web;

import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;
import com.mung.mungtique.reservation.application.port.in.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "mungshop 예약 생성")
    @PostMapping("/reservations")
    public ResponseEntity<Long> createReservation(@RequestBody @Valid ReservationReq reservationReq) {
        log.info("Reservation request: {}", reservationReq);
        return ResponseEntity.ok(reservationService.create(reservationReq));
    }

    @Operation(summary = "예약 번호로 예약 목록 조회")
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationRes> getReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservation(reservationId));
    }

    @Operation(summary = "특정 유저의 예약 목록 조회")
    @GetMapping("/reservations/user/{userId}")
    public ResponseEntity<List<ReservationRes>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }

    @Operation(summary = "예약 취소")
    @DeleteMapping("/reservations/{reservationId}/cancel")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable Long reservationId) {
        // soft delete 사용
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }
}
