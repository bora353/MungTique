package com.mung.mungtique.mungshop.application.service;

import com.mung.mungtique.mungshop.application.port.in.LikeService;
import com.mung.mungtique.mungshop.application.port.out.LikeRepoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    /**
     * redis는 원자적 연산 제공하며 트랜잭션 보장 안함
     */

    private final LikeRepoPort likeRepoPort;

    @Override
    public Long getLikeCount(Long mungShopId) {
        return likeRepoPort.getLikeCount(mungShopId);
    }

    @Override
    @Transactional
    public Long likeMungShop(Long mungShopId, Long userId) {
        return likeRepoPort.likeMungShopStatus(mungShopId, userId);
    }

    @Override
    @Transactional
    public Long unlikeMungShop(Long mungShopId, Long userId) {
        return likeRepoPort.unlikeMungShopStatus(mungShopId, userId);
    }

    @Override
    public boolean hasUserLiked(Long mungShopId, Long userId) {
        return likeRepoPort.hasUserLike(mungShopId, userId);
    }
}
