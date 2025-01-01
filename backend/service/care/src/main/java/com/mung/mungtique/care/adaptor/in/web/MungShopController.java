package com.mung.mungtique.care.adaptor.in.web;

import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.care.application.port.in.MungShopService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/care-service")
public class MungShopController {

    private final MungShopService mungShopService;

    @Operation(summary = "애견샵 정보를 모두 가져온다")
    @GetMapping("/mungshops")
    public ResponseEntity<List<MungShopRes>> getAllMungShops() {

        return ResponseEntity.ok(mungShopService.getAllMungShops());
    }
    
    @Operation(summary = "애견샵 좋아요 정보를 가져온다")
    @GetMapping("/mungshop-like-status")
    public ResponseEntity<Boolean> likeMungShopStatus(@RequestParam Long mungShopId, @RequestParam Long userId) {

        log.info("Like MungShop Status {}, {}", mungShopId, userId);
        return ResponseEntity.ok(mungShopService.likeMungShopStatus(mungShopId, userId));
    }
    
    @Operation(summary = "애견샵에 좋아요를 선택한다.")
    @PostMapping("/mungshop-like")
    public ResponseEntity<MungShopLikeRes> likeMungShop(@RequestBody MungShopLikeReq mungShopLikeReq) {

        log.info("Like MungShop" + mungShopLikeReq);
        return ResponseEntity.ok(mungShopService.likeMungShop(mungShopLikeReq));
    }
    
    @Operation(summary = "애견샵에 싫어요를 선택한다.")
    @PostMapping("/mungshop-unlike")
    public ResponseEntity<Boolean> unlikeMungShop(@RequestBody MungShopLikeReq mungShopLikeReq) {

        log.info("UnLike MungShop" + mungShopLikeReq);
        return ResponseEntity.ok(mungShopService.unlikeMungShop(mungShopLikeReq));
    }

}
