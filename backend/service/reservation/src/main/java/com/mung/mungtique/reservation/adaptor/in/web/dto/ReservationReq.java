package com.mung.mungtique.reservation.adaptor.in.web.dto;

import com.mung.mungtique.reservation.domain.ServiceType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationReq(
        @NotNull(message = "MungShop ID는 필수입니다.")
        Long mungShopId,

        @NotBlank(message = "MungShop 이름은 필수입니다.")
        String storeName,

        @NotBlank(message = "반려견 정보는 필수입니다.") String dogName,

        @NotBlank(message = "서비스 선택은 필수입니다.")
        ServiceType serviceType,

        @NotNull(message = "예약 날짜는 필수입니다.")
        @Future(message = "예약 날짜는 미래 날짜여야 합니다.")
        LocalDate reservationDate,

        @NotBlank(message = "예약 시간은 필수입니다.")
        String reservationTime,

        @NotNull(message = "User ID는 필수입니다.")
        Long userId,

        @NotBlank(message = "예약자 이름은 필수입니다.")
        String username,

        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,

        String requestMessage
) {}