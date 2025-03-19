package com.mung.mungtique.reservation.adaptor.out.persistence;

import com.mung.mungtique.reservation.domain.BreedType;
import com.mung.mungtique.reservation.domain.Reservation;
import com.mung.mungtique.reservation.domain.ReservationStatus;
import com.mung.mungtique.reservation.domain.ServiceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
@ActiveProfiles("test")
@Import(ReservationRepoAdaptor.class)
class ReservationRepoAdaptorTest {

    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private ReservationRepoAdaptor reservationRepoAdaptor;

    @DisplayName("userId로 Reservation 정보 가져온다.")
    @Test
    void findByUserId() {
        // given
        Reservation reservation1 = Reservation.builder()
                .reservationId(1L)
                .mungShopId(1L)
                .storeName("보라애견샵")
                .dogId(1L)
                .dogName("싼쵸")
                .breedType(BreedType.CHIHUAHUA)
                .serviceType(ServiceType.CUT)
                .reservationDate(LocalDate.of(2025,3,24))
                .reservationTime("11:00")
                .userId(999L)
                .username("이보라")
                .phone("01012345678")
                .requestMessage("잘 부탁드려요1")
                .build();
        Reservation reservation2 = Reservation.builder()
                .reservationId(1L)
                .mungShopId(1L)
                .storeName("보라애견샵")
                .dogId(1L)
                .dogName("싼쵸")
                .breedType(BreedType.CHIHUAHUA)
                .serviceType(ServiceType.CUT)
                .reservationDate(LocalDate.of(2025,3,24))
                .reservationTime("14:00")
                .userId(999L)
                .username("이보라")
                .phone("01012345678")
                .requestMessage("잘 부탁드려요2")
                .build();

        reservationRepoAdaptor.save(reservation1);
        reservationRepoAdaptor.save(reservation2);

        // when
        List<Reservation> reservations = reservationRepoAdaptor.findByUserId(999L);

        // then
        Assertions.assertThat(reservations).hasSize(2)
                .extracting("reservationStatus", "requestMessage", "reservationTime")
                .containsExactlyInAnyOrder(
                        tuple(ReservationStatus.WAITING_FOR_PAYMENT, "잘 부탁드려요1", "11:00"),
                        tuple(ReservationStatus.WAITING_FOR_PAYMENT, "잘 부탁드려요2", "14:00")
                );
    }

    @DisplayName("reservationId로 Reservation 정보 가져온다.")
    @Test
    void findById() {
        // given
        Reservation reservation = Reservation.builder()
                .reservationId(1L)
                .mungShopId(1L)
                .storeName("보라애견샵")
                .dogId(1L)
                .dogName("싼쵸")
                .breedType(BreedType.CHIHUAHUA)
                .serviceType(ServiceType.CUT)
                .reservationDate(LocalDate.of(2025,3,24))
                .reservationTime("10:00")
                .userId(999L)
                .username("이보라")
                .phone("01012345678")
                .requestMessage("잘 부탁드려요1")
                .build();

        reservationRepoAdaptor.save(reservation);

        // when
        Reservation result = reservationRepoAdaptor.findById(1L).orElseThrow();

        // then
        Assertions.assertThat(result)
                .extracting("reservationStatus", "requestMessage", "reservationTime")
                .containsExactly(ReservationStatus.WAITING_FOR_PAYMENT, "잘 부탁드려요1", "10:00");
    }
}