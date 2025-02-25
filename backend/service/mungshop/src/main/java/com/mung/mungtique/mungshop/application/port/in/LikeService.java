package com.mung.mungtique.mungshop.application.port.in;

public interface LikeService {
    Long getLikeCount(Long mungShopId);
    Long likeMungShop(Long mungShopId, Long userId);
    Long unlikeMungShop(Long mungShopId, Long userId);
    boolean hasUserLiked(Long mungShopId, Long userId);
}
