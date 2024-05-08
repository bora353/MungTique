package com.mung.mungtique.care.application.service;

import com.mung.mungtique.care.adaptor.in.web.dto.MungShopRes;
import com.mung.mungtique.care.application.port.in.MungShopService;
import com.mung.mungtique.care.application.port.out.MungShopRepoPort;
import com.mung.mungtique.care.application.service.mapper.MungShopMapper;
import com.mung.mungtique.care.domain.MungShop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
