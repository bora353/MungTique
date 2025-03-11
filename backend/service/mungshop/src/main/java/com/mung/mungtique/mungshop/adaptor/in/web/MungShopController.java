package com.mung.mungtique.mungshop.adaptor.in.web;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopService;
import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.ServiceType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class MungShopController {

    private final MungShopService mungShopService;

    @Operation(summary = "애견샵 정보를 모두 가져온다")
    @GetMapping("/mungshops")
    public ResponseEntity<List<MungShopRes>> getAllMungShops() {

        return ResponseEntity.ok(mungShopService.getAllMungShops());
    }

    @Operation(summary = "애견샵 검색 기능으로 필요한 정보만 가져온다")
    @GetMapping("/mungshops/search")
    public ResponseEntity<List<MungShopRes>> searchMungShops(@RequestParam("searchQuery") String searchQuery) {
        log.info("mungshop search : {}", searchQuery);
        return ResponseEntity.ok(mungShopService.findMungShopsByQuery(searchQuery));
    }

    @Operation(summary = "애견샵에서 품종과 서비스타입에 알맞은 가격 가져온다")
    @GetMapping("/mungshops/price")
    public ResponseEntity<Integer> getPrice(@RequestParam Long mungShopId, @RequestParam BreedType breedType, @RequestParam ServiceType serviceType) {
        log.info("mungshop price : mungShopId {} breed {}, serviceType {}", mungShopId, breedType, serviceType);
        return ResponseEntity.ok(mungShopService.getPrice(mungShopId, breedType, serviceType));
    }
}
