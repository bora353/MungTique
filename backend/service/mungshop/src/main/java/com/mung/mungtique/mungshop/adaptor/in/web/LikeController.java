package com.mung.mungtique.mungshop.adaptor.in.web;

import com.mung.mungtique.mungshop.application.port.in.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "애견샵 좋아요 개수 조회")
    @GetMapping("/mungshops/like-status")
    public ResponseEntity<Long> likeMungShopStatus(@RequestParam Long mungShopId) {
        log.info("like status for MungShopId: {}", mungShopId);
        Long likeCount =  likeService.getLikeCount(mungShopId);
        log.info("ALL like count : {}", likeCount);
        return ResponseEntity.ok(likeCount);
    }
    
    @Operation(summary = "애견샵 좋아요 추가")
    @PostMapping("/mungshops/{mungShopId}/like/{userId}")
    public ResponseEntity<Long> likeMungShop(@PathVariable Long mungShopId,
                                                        @PathVariable Long userId) {
        log.info("Like --- UserId: {}, MungShopId: {}", userId, mungShopId);
        Long likeCount = likeService.likeMungShop(mungShopId, userId);
        log.info("like count : {}", likeCount);
        return ResponseEntity.ok(likeCount);
    }
    
    @Operation(summary = "애견샵 좋아요 취소")
    @DeleteMapping("/mungshops/{mungShopId}/unlike/{userId}")
    public ResponseEntity<Long> unlikeMungShop(@PathVariable Long mungShopId,
                                                  @PathVariable Long userId) {
        log.info("Unlike --- UserId: {}, MungShopId: {}", userId, mungShopId);
        Long likeCount = likeService.unlikeMungShop(mungShopId, userId);
        log.info("Unlike count : {}", likeCount);
        return ResponseEntity.ok(likeCount);
    }

    @Operation(summary = "특정 유저가 좋아요 눌렀는지 확인")
    @GetMapping("/mungshops/{mungShopId}/like-status/{userId}")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Long mungShopId, @PathVariable(required = false) Long userId) {
        if (userId == null) return ResponseEntity.ok(false);
        boolean liked = likeService.hasUserLiked(mungShopId, userId);
        log.info("like status for MungShopId: {} by UserId: {}", mungShopId, userId);
        return ResponseEntity.ok(liked);
    }
}
