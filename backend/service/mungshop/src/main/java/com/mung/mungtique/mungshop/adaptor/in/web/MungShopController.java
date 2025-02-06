package com.mung.mungtique.mungshop.adaptor.in.web;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopService;
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
public class MungShopController {

    private final MungShopService mungShopService;

    @Operation(summary = "애견샵 정보를 모두 가져온다")
    @GetMapping("/mungshops")
    public ResponseEntity<List<MungShopRes>> getAllMungShops() {

        return ResponseEntity.ok(mungShopService.getAllMungShops());
    }
    
    @Operation(summary = "애견샵 좋아요 정보를 가져온다")
    @GetMapping("/mungshops/like-status")
    public ResponseEntity<Boolean> likeMungShopStatus(@RequestParam Long mungShopId, @RequestParam Long userId) {

        log.info("like status for MungShopId: {} by UserId: {}", mungShopId, userId);
        return ResponseEntity.ok(mungShopService.likeMungShopStatus(mungShopId, userId));
    }
    
    @Operation(summary = "애견샵에 좋아요를 선택한다.")
    @PostMapping("/mungshops/{mungShopId}/like")
    public ResponseEntity<MungShopLikeRes> likeMungShop(@PathVariable Long mungShopId,
                                                        @RequestBody Long userId) {
        log.info("UserId: {} is liking MungShopId: {}", userId, mungShopId);
        return ResponseEntity.ok(mungShopService.likeMungShop(mungShopId, userId));
    }
    
    @Operation(summary = "애견샵에 싫어요를 선택한다.")
    @PostMapping("/mungshops/{mungShopId}/unlike")
    public ResponseEntity<Boolean> unlikeMungShop(@PathVariable Long mungShopId,
                                                        @RequestBody Long userId) {
        log.info("UserId: {} is unliking MungShopId: {}", userId, mungShopId);
        return ResponseEntity.ok(mungShopService.unlikeMungShop(mungShopId, userId));
    }
}
