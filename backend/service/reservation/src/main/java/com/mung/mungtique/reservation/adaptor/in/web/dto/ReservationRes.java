package com.mung.mungtique.reservation.adaptor.in.web.dto;

import com.mung.mungtique.reservation.domain.ReservationStatus;
import com.mung.mungtique.reservation.domain.ServiceType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRes(
        Long mungShopId,
        String storeName,
        ServiceType serviceType,
        LocalDate reservationDate,
        String reservationTime,
        Long userId,
        String username,
        String phone,
        String requestMessage,
        ReservationStatus reservationStatus
) {}