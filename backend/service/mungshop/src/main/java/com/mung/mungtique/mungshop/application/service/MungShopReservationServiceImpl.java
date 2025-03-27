package com.mung.mungtique.mungshop.application.service;

import com.mung.mungtique.mungshop.adaptor.in.message.dto.MungShopConfirmMessage;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.reservation.MungShopReservationRes;
import com.mung.mungtique.mungshop.application.port.in.MungShopReservationService;
import com.mung.mungtique.mungshop.application.port.out.MungShopRepoPort;
import com.mung.mungtique.mungshop.application.port.out.MungShopReservationRepoPort;
import com.mung.mungtique.mungshop.application.service.mapper.MungShopMapperImpl;
import com.mung.mungtique.mungshop.domain.MungShop;
import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.MungShopReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class MungShopReservationServiceImpl implements MungShopReservationService {

    private final MungShopReservationRepoPort reservationRepoPort;
    private final MungShopRepoPort mungShopRepoPort;
    private final MungShopMapperImpl mapper;
    private final Clock clock = Clock.systemDefaultZone();

    @Override
    public List<MungShopReservationRes> getAvailableReservationInfo(Long mungShopId) {
        List<MungShopReservation> mungShopReservations = reservationRepoPort.findByMungShopIdAndStatus(mungShopId, MungShopReservationStatus.AVAILABLE);
        return mapper.toMungShopReservationRes(mungShopReservations);
    }

    @Transactional
    @Override
    public Boolean lockAndCheckAvailability(Long mungShopId, LocalDate reservationDate, String reservationTime) {
        int updatedRows = reservationRepoPort.updateStatusIfAvailable(
                                                                        mungShopId,
                                                                        reservationDate,
                                                                        reservationTime,
                                                                        MungShopReservationStatus.AVAILABLE,
                                                                        MungShopReservationStatus.PENDING_PAYMENT
                                                                        );

        return updatedRows > 0;
    }

    @Transactional
    @Override
    public MungShopReservation confirmReservation(MungShopConfirmMessage message) {
        log.info(message.mungShopId());
        MungShopReservation reservation = reservationRepoPort.findByMungShopIdAndReservationDateAndReservationTime(
                message.mungShopId(),
                message.reservationDate(),
                message.reservationTime()).orElseThrow(() -> new IllegalStateException("예약을 찾을 수 없습니다."));
        log.info("reservation: {}", reservation);
        return reservation.confirmReservation();
    }

    @Transactional
    @Override
    public int cancelExpiredReservations() {
        LocalDateTime expirationTime = LocalDateTime.now(clock).minusMinutes(10);

        List<MungShopReservation> expiredReservations = reservationRepoPort.findReservationsWithStatusUpdatedBefore(
                MungShopReservationStatus.PENDING_PAYMENT,
                expirationTime
        );

        if (expiredReservations.isEmpty()) return 0;

        expiredReservations.forEach(MungShopReservation::changeAvailableTime);
        return expiredReservations.size();
    }

    @Transactional
    @Override
    public void initializeReservationSlots() {
        List<MungShop> shops = mungShopRepoPort.findAll();
        LocalDate today = LocalDate.now(clock);

        List<MungShopReservation> reservations = new ArrayList<>();

        // 내일부터 7일간 데이터 생성
        for (int dayOffset = 1; dayOffset <= 7; dayOffset++) {
            LocalDate date = today.plusDays(dayOffset);

            for (MungShop shop : shops) {
                for (int hour = 9; hour <= 18; hour++) {
                    String time = String.format("%02d:00", hour);

                    MungShopReservation reservation = MungShopReservation.builder()
                            .mungShopId(shop.getMungShopId())
                            .reservationDate(date)
                            .reservationTime(time)
                            .status(MungShopReservationStatus.AVAILABLE)
                            .build();

                    reservations.add(reservation);
                }
            }
        }
        reservationRepoPort.saveAll(reservations);
    }
}

