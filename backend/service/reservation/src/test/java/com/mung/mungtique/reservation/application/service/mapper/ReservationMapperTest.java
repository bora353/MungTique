package com.mung.mungtique.reservation.application.service.mapper;

import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationReq;
import com.mung.mungtique.reservation.adaptor.in.web.dto.ReservationRes;
import com.mung.mungtique.reservation.domain.BreedType;
import com.mung.mungtique.reservation.domain.Reservation;
import com.mung.mungtique.reservation.domain.ReservationStatus;
import com.mung.mungtique.reservation.domain.ServiceType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationMapperTest {

    private final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    @Test
    void toReservation() {
        // Given
        ReservationReq reservationReq = createReservationReq();

        // When
        Reservation result = reservationMapper.toReservation(reservationReq);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getMungShopId()).isEqualTo(reservationReq.mungShopId());
        assertThat(result.getStoreName()).isEqualTo(reservationReq.storeName());
        assertThat(result.getDogName()).isEqualTo(reservationReq.dogName());
        assertThat(result.getBreedType()).isEqualTo(reservationReq.breedType());
        assertThat(result.getServiceType()).isEqualTo(reservationReq.serviceType());
        assertThat(result.getReservationDate()).isEqualTo(reservationReq.reservationDate());
        assertThat(result.getReservationTime()).isEqualTo(reservationReq.reservationTime());
        assertThat(result.getUserId()).isEqualTo(reservationReq.userId());
        assertThat(result.getUsername()).isEqualTo(reservationReq.username());
        assertThat(result.getPhone()).isEqualTo(reservationReq.phone());
        assertThat(result.getRequestMessage()).isEqualTo(reservationReq.requestMessage());
        assertThat(result.getReservationStatus()).isEqualTo(ReservationStatus.WAITING_FOR_PAYMENT);
    }

    @Test
    void toReservationResList() {
        // Given
        List<Reservation> reservationList = List.of(createReservation());

        // When
        List<ReservationRes> result = reservationMapper.toReservationResList(reservationList);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).storeName()).isEqualTo(reservationList.get(0).getStoreName());
        assertThat(result.get(0).dogName()).isEqualTo(reservationList.get(0).getDogName());
        assertThat(result.get(0).reservationStatus()).isEqualTo(ReservationStatus.WAITING_FOR_PAYMENT);
    }

    @Test
    void toReservationRes() {
        // Given
        Reservation reservation = createReservation();

        // When
        ReservationRes result = reservationMapper.toReservationRes(reservation);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.storeName()).isEqualTo(reservation.getStoreName());
        assertThat(result.dogName()).isEqualTo(reservation.getDogName());
        assertThat(result.reservationStatus()).isEqualTo(ReservationStatus.WAITING_FOR_PAYMENT);
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

    private static ReservationReq createReservationReq() {
        return ReservationReq.builder()
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
    }
}