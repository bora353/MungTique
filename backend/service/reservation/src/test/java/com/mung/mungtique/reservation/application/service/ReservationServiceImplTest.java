package com.mung.mungtique.reservation.application.service;

import com.mung.mungtique.reservation.adaptor.in.message.dto.PaymentSuccessMessage;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;
import com.mung.mungtique.reservation.adaptor.out.persistence.ReservationRepo;
import com.mung.mungtique.reservation.adaptor.out.persistence.ReservationRepoAdaptor;
import com.mung.mungtique.reservation.adaptor.out.web.MungShopApiAdaptor;
import com.mung.mungtique.reservation.application.service.mapper.ReservationMapperImpl;
import com.mung.mungtique.reservation.domain.BreedType;
import com.mung.mungtique.reservation.domain.Reservation;
import com.mung.mungtique.reservation.domain.ReservationStatus;
import com.mung.mungtique.reservation.domain.ServiceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ReservationServiceImplTest {

    @MockBean
    private MungShopApiAdaptor mungShopApiPort;
    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private ReservationRepoAdaptor reservationRepoPort;
    @Autowired
    private ReservationMapperImpl mapper;
    @Autowired
    private ReservationServiceImpl reservationService;

    @AfterEach
    void tearDown() {
        reservationRepo.deleteAllInBatch();
    }

    @DisplayName("예약 생성한다.")
    @Test
    void create() {
        // given
        ReservationReq reservationReq = ReservationReq.builder()
                .mungShopId(3L)
                .storeName("보라애견샵")
                .dogId(4L)
                .dogName("싼쵸")
                .breedType(BreedType.CHIHUAHUA)
                .serviceType(ServiceType.CUT)
                .reservationDate(LocalDate.of(2025,3,24))
                .reservationTime("12:00")
                .userId(5L)
                .username("이보라")
                .phone("01012345678")
                .requestMessage("잘 부탁드려요~")
                .build();

        when(mungShopApiPort.lockAndCheckAvailability(reservationReq.mungShopId(), reservationReq.reservationDate(), reservationReq.reservationTime())).thenReturn(true);

        // when
        long reservationId = reservationService.create(reservationReq);

        // then
        Assertions.assertThat(reservationId).isGreaterThan(0L);
    }

    @DisplayName("userId로 예약 정보 가져온다.")
    @Test
    void getReservationsByUser() {
        // given
        Reservation reservation = createReservation();

        ReservationRes reservationRes = ReservationRes.builder()
                .reservationId(1L)
                .reservationDate(LocalDate.of(2025,3,24))
                .reservationTime("10:00")
                .requestMessage("잘 부탁드려요")
                .reservationStatus(ReservationStatus.WAITING_FOR_PAYMENT)
                .build();

        reservationRepo.save(reservation);

        // when
        List<ReservationRes> resultList = reservationService.getReservationsByUser(999L);

        // then
        Assertions.assertThat(resultList)
                .extracting("requestMessage", "reservationStatus")
                .containsExactly(tuple("잘 부탁드려요", ReservationStatus.WAITING_FOR_PAYMENT));
    }

    @DisplayName("예약 취소한다.")
    @Test
    void cancelReservation() {
        // given
        Reservation reservation = createReservation();
        Reservation save = reservationRepo.save(reservation);
        Long reservationId = save.getReservationId();

        // when
        Boolean result = reservationService.cancelReservation(reservationId);

        // then
        Assertions.assertThat(result).isTrue();
        Reservation canceledReservation = reservationRepo.findById(reservationId).orElseThrow();
        Assertions.assertThat(canceledReservation.getReservationStatus()).isEqualTo(ReservationStatus.CANCELED);
    }

    @DisplayName("예약 결제 완료 처리한다.")
    @Test
    void updateReservationToPaid() {
        // given
        Long reservationId = 1L;
        Reservation reservation = createReservation();
        reservationRepo.save(reservation);

        PaymentSuccessMessage message = PaymentSuccessMessage.builder()
                                        .paymentId(1L)
                                        .reservationId(reservationId)
                                        .build();

        // when
        Reservation updatedReservation = reservationService.updateReservationToPaid(message);

        // then
        Assertions.assertThat(updatedReservation.getReservationStatus()).isEqualTo(ReservationStatus.PAID);  // 예약 상태가 'PAID'로 변경되었는지 확인
        Assertions.assertThat(updatedReservation.getReservationId()).isEqualTo(reservationId);
    }

    private Reservation createReservation() {
        return Reservation.builder()
                .reservationId(1L)
                .mungShopId(1L)
                .storeName("보라애견샵")
                .dogId(1L)
                .dogName("싼쵸")
                .breedType(BreedType.CHIHUAHUA)
                .serviceType(ServiceType.CUT)
                .reservationDate(LocalDate.of(2025, 3, 24))
                .reservationTime("10:00")
                .userId(999L)
                .username("이보라")
                .phone("01012345678")
                .requestMessage("잘 부탁드려요")
                .reservationStatus(ReservationStatus.PAID)
                .build();
    }
}