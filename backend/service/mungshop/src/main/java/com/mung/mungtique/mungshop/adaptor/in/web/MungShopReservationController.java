package com.mung.mungtique.mungshop.adaptor.in.web;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation.MungShopReservationRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MungShopReservationController {

    private final MungShopReservationService mungShopReservationService;

    @Operation(summary = "애견샵 예약 가능한 정보를 가져온다")
    @GetMapping("/mungshops/reservation/{mungShopId}/available")
    public ResponseEntity<List<MungShopReservationRes>> getAvailableReservationInfo(@PathVariable Long mungShopId) {
        log.info("Mungshop reservation : mungshopId {}", mungShopId);

        return ResponseEntity.ok(mungShopReservationService.getAvailableReservationInfo(mungShopId));
    }

    @Operation(summary = "애견샵 예약 가능한 시간인지 체크한다")
    @GetMapping("/mungshops/{mungShopId}/check-availability")
    public ResponseEntity<Boolean> lockAndCheckAvailability(
                                            @PathVariable Long mungShopId,
                                            @RequestParam String reservationTime) {
        log.info("Mungshop check time availability : mungshopId {} resevationTime {}", mungShopId, reservationTime);
        return ResponseEntity.ok(mungShopReservationService.lockAndCheckAvailability(mungShopId, reservationTime));
    }
}
