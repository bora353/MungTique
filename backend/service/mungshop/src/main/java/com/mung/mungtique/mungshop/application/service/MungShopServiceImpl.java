package com.mung.mungtique.mungshop.application.service;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopLikeRes;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopService;
import com.mung.mungtique.mungshop.application.port.out.MungShopRepoPort;
import com.mung.mungtique.mungshop.application.service.mapper.MungShopMapper;
import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopLike;
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
        return mungShops.stream().map(mapper::toMungShopDTO).toList();
    }

    @Override
    public List<MungShopRes> findMungShopsByQuery(String searchQuery) {
        List<MungShop> mungShops = mungShopRepoPort.findByStoreName(searchQuery);
        return mungShops.stream().map(mapper::toMungShopDTO).toList();
    }

    @Override
    public Boolean likeMungShopStatus(Long mungShopId, Long userId) {
        return mungShopRepoPort.findByMungShopMungShopIdAndUserId(mungShopId, userId).isPresent();
    }

    @Override
    @Transactional

    public MungShopLikeRes likeMungShop(Long mungShopId, Long userId) {

        MungShop mungShop = mungShopRepoPort.findById(mungShopId).orElseThrow(() -> new EntityNotFoundException("MungShop with id " + mungShopId + " not found"));

        MungShopLike mungShopLike = MungShopLike.builder()
                .userId(userId)
                .mungShop(mungShop)
                .build();

        MungShopLike savedMungShopLike = mungShopRepoPort.save(mungShopLike);

        return mapper.toLikeDTO(savedMungShopLike);
    }

    @Override
    @Transactional
    public boolean unlikeMungShop(Long mungShopId, Long userId) {
        MungShopLike existingLike = mungShopRepoPort.findByMungShopMungShopIdAndUserId(mungShopId, userId)
                                    .orElseThrow(() -> new NoSuchElementException("No MungShopLike found with the given MungShopId and UserId"));

        mungShopRepoPort.delete(existingLike);
        return true;
    }
}
