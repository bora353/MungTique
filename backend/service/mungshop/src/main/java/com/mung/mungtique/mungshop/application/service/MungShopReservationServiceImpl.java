package com.mung.mungtique.mungshop.application.service;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation.MungShopReservationRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopReservationService;
import com.mung.mungtique.mungshop.application.port.out.MungShopReservationRepoPort;
import com.mung.mungtique.mungshop.application.service.mapper.MungShopMapper;
import com.mung.mungtique.mungshop.application.service.mapper.MungShopMapperImpl;
import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.ReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class MungShopReservationServiceImpl implements MungShopReservationService {

    private final MungShopReservationRepoPort mungShopReservationRepoPort;
    private final MungShopMapperImpl mapper;

    @Override
    public List<MungShopReservationRes> getAvailableReservationInfo(Long mungShopId) {
        List<MungShopReservation> mungShopReservations = mungShopReservationRepoPort.findByMungShopIdAndStatus(mungShopId, ReservationStatus.NOT_RESERVED);
        log.info("mungShopReservations: {}", mungShopReservations);
        return mapper.toMungShopReservationRes(mungShopReservations);
    }
}
