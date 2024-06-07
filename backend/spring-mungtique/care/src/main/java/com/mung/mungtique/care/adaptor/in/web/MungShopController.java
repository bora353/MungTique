package com.mung.mungtique.care.adaptor.in.web;

import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.care.application.port.in.MungShopService;
import com.mung.mungtique.care.domain.MungShopLike;
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


    @GetMapping("/care/mungshops")
    public ResponseEntity<List<MungShopRes>> getAllMungShops() {

        return ResponseEntity.ok(mungShopService.getAllMungShops());
    }

    @GetMapping("/care/mungshop-like-status")
    public ResponseEntity<Boolean> likeMungShopStatus(@RequestParam Long mungShopId, @RequestParam Long userId) {

        log.info("Like MungShop Status {}, {}", mungShopId, userId);
        return ResponseEntity.ok(mungShopService.likeMungShopStatus(mungShopId, userId));
    }

    @PostMapping("/care/mungshop-like")
    public ResponseEntity<MungShopLikeRes> likeMungShop(@RequestBody MungShopLikeReq mungShopLikeReq) {

        log.info("Like MungShop" + mungShopLikeReq);
        return ResponseEntity.ok(mungShopService.likeMungShop(mungShopLikeReq));
    }

    @PostMapping("/care/mungshop-unlike")
    public ResponseEntity<Boolean> unlikeMungShop(@RequestBody MungShopLikeReq mungShopLikeReq) {

        log.info("UnLike MungShop" + mungShopLikeReq);
        return ResponseEntity.ok(mungShopService.unlikeMungShop(mungShopLikeReq));
    }

}
