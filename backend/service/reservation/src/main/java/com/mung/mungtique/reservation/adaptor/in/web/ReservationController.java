package com.mung.mungtique.reservation.adaptor.in.web;

import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
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
public class ReservationController {

    //private final ReservationService reservationService;

    @Operation(summary = "mungshop에 예약을 요청한다")
    @PostMapping("/reservations")
    //public ResponseEntity<ReservationRes> createReservation(@RequestBody @Valid ReservationReq reservationReq) {
    public ResponseEntity createReservation(@RequestBody @Valid ReservationReq reservationReq) {
        log.info("Reservation request: {}", reservationReq);
        return ResponseEntity.ok().build();
        //return ResponseEntity.ok(reservationService.createReservation(reservationReq));
    }
}
