package com.mung.mungtique.reservation.adaptor.in.web.dto;

import com.mung.mungtique.reservation.domain.BreedType;
import com.mung.mungtique.reservation.domain.ReservationStatus;
import com.mung.mungtique.reservation.domain.ServiceType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservationRes(
        Long reservationId,
        Long mungShopId,
        String storeName,
        Long dogId,
        String dogName,
        ServiceType serviceType,
        BreedType breedType,
        LocalDate reservationDate,
        String reservationTime,
        Long userId,
        String username,
        String phone,
        String requestMessage,
        ReservationStatus reservationStatus
) {}