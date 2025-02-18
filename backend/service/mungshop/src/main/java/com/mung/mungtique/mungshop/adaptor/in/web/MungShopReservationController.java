package com.mung.mungtique.mungshop.adaptor.in.web;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation.MungShopReservationRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
