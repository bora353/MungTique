package com.mung.mungtique.mungshop.application.port.out;

public interface LikeRepoPort {

    Long likeMungShopStatus(Long mungShopId, Long userId);
    Long unlikeMungShopStatus(Long mungShopId, Long userId);
    Long getLikeCount(Long mungShopId);
    boolean hasUserLike(Long mungShopId, Long userId);
}
