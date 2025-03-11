package com.mung.mungtique.mungshop.application.service;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.mungshop.MungShopRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopService;
import com.mung.mungtique.mungshop.application.port.out.MungShopRepoPort;
import com.mung.mungtique.mungshop.application.port.out.PriceRepoPort;
import com.mung.mungtique.mungshop.application.service.mapper.MungShopMapperImpl;
import com.mung.mungtique.mungshop.domain.BreedType;
import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopPrice;
import com.mung.mungtique.mungshop.domain.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MungShopServiceImpl implements MungShopService {

    private final MungShopRepoPort mungShopRepoPort;
    private final PriceRepoPort priceRepoPort;
    private final MungShopMapperImpl mapper;

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
    public Integer getPrice(Long mungShopId, BreedType breedType, ServiceType serviceType) {
        MungShopPrice mungShopPrice = priceRepoPort.findPriceByShopAndType(mungShopId, breedType, serviceType).orElseThrow(() -> new IllegalStateException("MungShop not found"));
        return mungShopPrice.getPrice();
    }
}
