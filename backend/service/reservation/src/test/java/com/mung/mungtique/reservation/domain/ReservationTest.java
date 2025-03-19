package com.mung.mungtique.reservation.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    void confirmPaymentTest() {
        // Given
        Reservation reservation = createReservation();
        Long paymentId = 123L;

        // When
        reservation.confirmPayment(paymentId);

        // Then
        assertThat(reservation.getPaymentId()).isEqualTo(paymentId);
        assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.PAID);
    }

    @Test
    void cancelTest() {
        // Given
        Reservation reservation = createReservation();

        // When
        reservation.cancel();

        // Then
        assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.CANCELED);
        assertThat(reservation.getIsDeleted()).isTrue();
    }

    private static Reservation createReservation() {
        return Reservation.builder()
                .reservationId(1L)
                .mungShopId(100L)
                .storeName("보라애견샵")
                .dogId(200L)
                .dogName("싼쵸")
                .breedType(BreedType.CHIHUAHUA)
                .serviceType(ServiceType.CUT)
                .reservationDate(LocalDate.of(2025, 3, 24))
                .reservationTime("10:00")
                .userId(300L)
                .username("이보라")
                .phone("01012345678")
                .requestMessage("잘 부탁드려요")
                .reservationStatus(ReservationStatus.WAITING_FOR_PAYMENT)
                .isDeleted(false)
                .paymentId(null)
                .build();
    }
}
