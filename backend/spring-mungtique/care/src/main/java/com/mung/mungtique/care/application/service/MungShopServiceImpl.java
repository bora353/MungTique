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

        return mapper.domainListToDtoList(mungShops);
    }

    @Override
    public Boolean likeMungShopStatus(Long mungShopId, Long userId) {
        Optional<MungShopLike> existingLike = mungShopRepoPort.findByMungShopMungShopIdAndUserId(mungShopId, userId);

        return existingLike.isPresent();
    }

    @Override
    public MungShopLikeRes likeMungShop(MungShopLikeReq mungShopLikeReq) {

        Optional<MungShop> optionalMungShop = mungShopRepoPort.findById(mungShopLikeReq.mungShopId());

        if (optionalMungShop.isPresent()) {
            MungShop mungShop = optionalMungShop.get();

            MungShopLike mungShopLike = MungShopLike.builder()
                    .userId(mungShopLikeReq.userId())
                    .mungShop(mungShop)
                    .build();

            MungShopLike savedMungShopLike = mungShopRepoPort.save(mungShopLike);

            return mapper.domainToDto(savedMungShopLike);
        } else {
            throw new EntityNotFoundException("MungShop with id " + mungShopLikeReq.mungShopId() + " not found");
        }
    }

    @Override
    public boolean unlikeMungShop(MungShopLikeReq mungShopLikeReq) {
        Long mungShopId = mungShopLikeReq.mungShopId();
        Long userId = mungShopLikeReq.userId();

        Optional<MungShopLike> existingLike = mungShopRepoPort.findByMungShopMungShopIdAndUserId(mungShopId, userId);

        if (existingLike.isPresent()) {
            mungShopRepoPort.delete(existingLike.get());
            return true;
        } else {
            return false;
        }
    }


}
