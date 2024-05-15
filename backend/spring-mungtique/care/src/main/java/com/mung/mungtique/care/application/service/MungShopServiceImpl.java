package com.mung.mungtique.care.application.service;

import com.mung.mungtique.care.adaptor.in.web.dto.MungShopLikeReq;
import com.mung.mungtique.care.adaptor.in.web.dto.MungShopRes;
import com.mung.mungtique.care.application.port.in.MungShopService;
import com.mung.mungtique.care.application.port.out.MungShopRepoPort;
import com.mung.mungtique.care.application.service.mapper.MungShopMapper;
import com.mung.mungtique.care.domain.MungShop;
import com.mung.mungtique.care.domain.MungShopLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MungShopServiceImpl implements MungShopService {

    private final MungShopRepoPort mungShopRepoPort;
    private final MungShopMapper mapper;

    @Override
    public List<MungShopRes> getAllMungShops() {
        List<MungShop> mungShops = mungShopRepoPort.findAll();

        return mapper.domainToDto(mungShops);
    }

    @Override
    public Boolean likeMungShopStatus(Long mungShopId, Long userId) {
        Optional<MungShopLike> existingLike = mungShopRepoPort.findByMungShopIdAndUserId(mungShopId, userId);

        return existingLike.isPresent();
    }

    @Override
    public MungShopLike likeMungShop(MungShopLikeReq mungShopLikeReq) {

        MungShopLike mungShopLike = MungShopLike.builder()
                .userId(mungShopLikeReq.userId())
                .mungShopId(mungShopLikeReq.mungShopId())
                .build();

        return mungShopRepoPort.save(mungShopLike);
    }

    @Override
    public boolean unlikeMungShop(MungShopLikeReq mungShopLikeReq) {
        Long mungShopId = mungShopLikeReq.mungShopId();
        Long userId = mungShopLikeReq.userId();

        Optional<MungShopLike> existingLike = mungShopRepoPort.findByMungShopIdAndUserId(mungShopId, userId);

        if (existingLike.isPresent()) {
            mungShopRepoPort.delete(existingLike.get());
            return true;
        } else {
            return false;
        }
    }


}
