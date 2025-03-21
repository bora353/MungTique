package com.mung.mungtique.reservation.application.service;

import com.mung.mungtique.reservation.adaptor.in.message.dto.PaymentSuccessMessage;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;
import com.mung.mungtique.reservation.adaptor.out.message.dto.MungShopConfirmMessage;
import com.mung.mungtique.reservation.application.port.in.ReservationService;
import com.mung.mungtique.reservation.application.port.out.message.MungShopEventPort;
import com.mung.mungtique.reservation.application.port.out.persistence.ReservationRepoPort;
import com.mung.mungtique.reservation.application.port.out.web.MungShopApiPort;
import com.mung.mungtique.reservation.application.service.mapper.ReservationMapperImpl;
import com.mung.mungtique.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final MungShopApiPort mungShopApiPort;
    private final ReservationRepoPort reservationRepoPort;
    private final ReservationMapperImpl mapper;
    private final MungShopEventPort mungShopEventPort;

    @Override
    @Transactional
    public Long create(ReservationReq request) {
        // MungShop 서비스에서 예약 가능 시간 확인
        boolean isAvailable = mungShopApiPort.lockAndCheckAvailability(request.mungShopId(), request.reservationTime());
        log.info("isAvailable: {}", isAvailable);

        if (!isAvailable) {
            throw new IllegalStateException("선택한 시간은 예약이 불가능합니다 : " + request.reservationTime());
        }

        // 예약 생성 (WAITING_FOR_PAYMENT 상태)
        Reservation reservation = mapper.toReservation(request);
        reservation = reservationRepoPort.save(reservation);

        return reservation.getReservationId();
    }

    @Override
    public List<ReservationRes> getReservationsByUser(Long userId) {
        List<Reservation> reservations = reservationRepoPort.findByUserId(userId);
        return mapper.toReservationResList(reservations);
    }

    /**
     * 결제 취소 서비스에서 카프카 메세지 받아서 예약취소도 진행
     */
    @Override
    @Transactional
    public Boolean cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepoPort.findById(reservationId).orElseThrow(() -> new IllegalStateException("예약 정보를 찾을 수 없습니다."));

        // 소프트 딜리트 (상태 변경)
        reservation.cancel();
        return true;
    }

    @Override
    public ReservationRes getReservation(Long reservationId) {
        Reservation reservation = reservationRepoPort.findById(reservationId).orElseThrow(() -> new IllegalStateException("예약번호로 예약정보를 찾을 수 없습니다."));
        return mapper.toReservationRes(reservation);
    }

    @Override
    @Transactional
    public Reservation updateReservationToPaid(PaymentSuccessMessage message) {
        Reservation reservation = reservationRepoPort.findById(message.reservationId()).orElseThrow(() -> new IllegalStateException("예약번호로 예약정보를 찾을 수 없습니다."));

        log.info("결제 ID {}로 결제가 완료되었습니다.", message.paymentId());
        reservation.confirmPayment(message.paymentId());
        log.info("예약 상태가 'PAID'로 변경되었습니다.");

        return reservation;
    }

    @Override
    public void sendReservationConfirmToMungShop(PaymentSuccessMessage message) {
        Reservation reservation = reservationRepoPort.findById(message.reservationId()).orElseThrow(() -> new IllegalStateException("예약번호로 예약정보를 찾을 수 없습니다."));
        MungShopConfirmMessage event = mapper.toMungShopConfirmMessage(reservation.getMungShopId(), reservation.getReservationDate(), reservation.getReservationTime());
        mungShopEventPort.sendReservationConfirmToMungShop(event);
    }
}
