package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.application.port.out.LikeRepoPort;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LikeRepoAdaptor implements LikeRepoPort {

    /**
     * 유저 좋아요 목록 : 자료구조 set (연산 SADD, SREM, SISMEMBER 사용)
     * 좋아요 개수 : 자료구조 string (연산 INCR, DECR, GET 사용)
     */

    private final StringRedisTemplate redisTemplate;

    // 좋아요 증가 (INCR)
    public Long likeMungShopStatus(Long mungShopId, Long userId) {
        Boolean alreadyLiked = redisTemplate.opsForSet().isMember(getUserSetKey(mungShopId), userId.toString());
        if (Boolean.TRUE.equals(alreadyLiked)) {
            return getLikeCount(mungShopId);
        }

        redisTemplate.opsForSet().add(getUserSetKey(mungShopId), userId.toString());
        return redisTemplate.opsForValue().increment(getLikeKey(mungShopId));
    }

    // 좋아요 취소 (DECR)
    public Long unlikeMungShopStatus(Long mungShopId, Long userId) {
        Boolean liked = redisTemplate.opsForSet().isMember(getUserSetKey(mungShopId), userId.toString());
        if (Boolean.FALSE.equals(liked)) {
            return getLikeCount(mungShopId);
        }

        redisTemplate.opsForSet().remove(getUserSetKey(mungShopId), userId.toString());
        return redisTemplate.opsForValue().decrement(getLikeKey(mungShopId));
    }

    // 좋아요 개수 조회 (GET)
    public Long getLikeCount(Long mungShopId) {
        String count = redisTemplate.opsForValue().get(getLikeKey(mungShopId));
        return count != null? Long.parseLong(count) : 0L;
    }

    // 특정 유저가 좋아요 눌렀는지 확인 (SISMEMBER)
    public boolean hasUserLike(Long mungShopId, Long userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(getUserSetKey(mungShopId), userId.toString()));
    }

    private String getUserSetKey(Long mungShopId) {
        return "mungshop:liked_users:" + mungShopId;
    }

    private String getLikeKey(Long mungShopId) {
        return "mungshop:like:" + mungShopId;
    }
}
