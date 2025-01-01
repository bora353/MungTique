package com.mung.mungtique.care.application.service;

import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.care.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.care.application.port.in.MungShopService;
import com.mung.mungtique.care.application.port.out.MungShopRepoPort;
import com.mung.mungtique.care.application.service.mapper.MungShopMapper;
import com.mung.mungtique.care.domain.MungShop;
import com.mung.mungtique.care.domain.MungShopLike;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MungShopServiceImpl implements MungShopService {

    private final MungShopRepoPort mungShopRepoPort;
    private final MungShopMapper mapper;

    @Override
    public List<MungShopRes> getAllMungShops() {
        List<MungShop> mungShops = mungShopRepoPort.findAll();

        return mapper.domainListToDtoList(mungShops);
    }

    @Override
    public Boolean likeMungShopStatus(Long mungShopId, Long userId) {
        return mungShopRepoPort.findByMungShopMungShopIdAndUserId(mungShopId, userId).isPresent();
    }

    @Override
    @Transactional
    public MungShopLikeRes likeMungShop(MungShopLikeReq mungShopLikeReq) {

        MungShop mungShop = mungShopRepoPort.findById(mungShopLikeReq.mungShopId()).orElseThrow(() -> new EntityNotFoundException("MungShop with id " + mungShopLikeReq.mungShopId() + " not found"));

        MungShopLike mungShopLike = MungShopLike.builder()
                .userId(mungShopLikeReq.userId())
                .mungShop(mungShop)
                .build();

        MungShopLike savedMungShopLike = mungShopRepoPort.save(mungShopLike);

        return mapper.domainToDto(savedMungShopLike);
    }

    @Override
    @Transactional
    public boolean unlikeMungShop(MungShopLikeReq mungShopLikeReq) {
        Long mungShopId = mungShopLikeReq.mungShopId();
        Long userId = mungShopLikeReq.userId();

        MungShopLike existingLike = mungShopRepoPort.findByMungShopMungShopIdAndUserId(mungShopId, userId)
                                    .orElseThrow(() -> new NoSuchElementException("No MungShopLike found with the given MungShopId and UserId"));

        mungShopRepoPort.delete(existingLike);
        return true;
    }
}
